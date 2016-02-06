package au.com.goielts.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import au.com.goielts.model.Assessment;
import au.com.goielts.model.Course;
import au.com.goielts.model.Task;
import au.com.goielts.model.UploadedFile;
import au.com.goielts.model.User;
import au.com.goielts.services.AssessmentService;
import au.com.goielts.services.TaskService;
import au.com.goielts.services.UploadedFileService;
import au.com.goielts.services.UserService;

@Controller
public class TaskController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;
	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	private UploadedFileService uploadedFileService;

	@RequestMapping(value = "/task/view/{id}", method = RequestMethod.GET)
	public String view(Model model, @PathVariable int id, HttpServletRequest request) {
		User user = getAuthenticatedUser();
		Task task = taskService.with("assessments").findById(id);
		Course course = task.getCourse();
		model.addAttribute("task", task);
		model.addAttribute("course", course);
		Assessment assessment = assessmentService.findByTaskIdAndStudentId(id, user.getId());
		Set<Assessment> assessments = task.getAssessments();
		model.addAttribute("assessments", assessments);
		System.out.println(assessment);
		model.addAttribute("assessment", assessment);
		if(request.isUserInRole("STUDENT")){
			return "/task/view";
		}else{
			return "/teacher/task/view";
		}
	}

	@RequestMapping(value = "/task/{id}/assignment/upload", method = RequestMethod.POST)
	public String handleFileUpload(Model model, @RequestParam("file") MultipartFile file, @PathVariable int id,
			HttpServletRequest request) {
		if (!file.isEmpty()) {
			try {
				// saving the file
				byte[] bytes = file.getBytes();
				String filePath = String.format("/files/assessments/task_%d/user_%s/%s", id,
						getAuthenticatedUser().getId(), file.getOriginalFilename());
				String realPath = "C:/Users/slee/workspace/SoftwareDevelopmentProject" + filePath;
				File destination = new File(realPath);
				destination.getParentFile().mkdirs();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(destination, false));
				stream.write(bytes);
				stream.close();

				
				Task task = taskService.findById(id);
				Assessment assessment = assessmentService.findByTaskIdAndStudentId(id, getAuthenticatedUser().getId());
				
				
				if (assessment == null) {
					assessment = new Assessment();
					assessment.setStudent(getAuthenticatedUser());
					assessment.setTask(task);
					assessmentService.save(assessment);
				}
				
				UploadedFile uploadedFile = new UploadedFile();
				// populating fields
				uploadedFile.setFileName(file.getOriginalFilename());
				uploadedFile.setPath(filePath);
				uploadedFile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
				uploadedFile.setSize(file.getSize());
				
				uploadedFileService.save(uploadedFile);
				System.out.println(uploadedFile);

				assessment.setAssessmentFile(uploadedFile);
				

				assessmentService.update(assessment);

				System.out.println("File uploaded successfully");
				return "redirect:/task/view/" + id;
			} catch (Exception e) {
				System.out.println("You failed to upload the file => " + e.getMessage());
				return "redirect:/task/view/" + id;
			}
		} else {
			model.addAttribute("message", "You failed to upload the file, because the file was empty.");
			return "redirect:/task/view/" + id;
		}
	}

	@ModelAttribute("authUser")
	private User getAuthenticatedUser() {
		return userService.findByEmail(getPrincipal());
	}

	@ModelAttribute("tasks")
	private List<Task> getTasks(@PathVariable int id) {
		Task task = taskService.findById(id);

		List<Task> tasks = taskService.findAllByCourseId(task.getCourse().getId());
		return tasks;
	}

	private String getPrincipal() {
		String userName = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
