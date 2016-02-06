package au.com.goielts.controller.teacher;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import au.com.goielts.core.UserPrincipal;
import au.com.goielts.model.Assessment;
import au.com.goielts.model.Task;
import au.com.goielts.model.UploadedFile;
import au.com.goielts.model.User;
import au.com.goielts.services.AssessmentService;
import au.com.goielts.services.UploadedFileService;

@Controller
public class TeacherAssessmentController {
	
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private UploadedFileService uploadedFileService;
	
	
	@Autowired
    private Environment environment;
	
	@RequestMapping(value = "/teacher/assessment/upload_marked/{id}", method = RequestMethod.GET)
	public String view(Model model, @PathVariable int id, HttpServletRequest request) {
		Assessment assessment = assessmentService.findById(id);
		model.addAttribute("assessment", assessment);
		
		Task task = assessment.getTask();
		model.addAttribute("task", task);
		return "/teacher/assessment/view";
	}
	
	@RequestMapping(value = "/teacher/assessment/upload_marked/{id}", method = RequestMethod.POST)
	public String handleFileUpload(Model model, @RequestParam("file") MultipartFile file, @RequestParam("mark") int mark, @PathVariable int id,
			HttpServletRequest request) {
		if (!file.isEmpty()) {
			try {
				// saving the file
				byte[] bytes = file.getBytes();
				String filePath = String.format("/files/marked_assessments/task_%d/user_%s/%s", id,
						getAuthenticatedUser().getId(), file.getOriginalFilename());
				
				String serverPath = environment.getRequiredProperty("serverPath");
				String realPath = serverPath+filePath;
				File destination = new File(realPath);
				destination.getParentFile().mkdirs();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(destination, false));
				stream.write(bytes);
				stream.close();

				
				
				Assessment assessment = assessmentService.findById(id);
				
				
				UploadedFile uploadedFile = new UploadedFile();
				// populating fields
				uploadedFile.setFileName(file.getOriginalFilename());
				uploadedFile.setPath(filePath);
				uploadedFile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
				uploadedFile.setSize(file.getSize());
				
				uploadedFileService.save(uploadedFile);
				System.out.println(uploadedFile);

				assessment.setMarkedFile(uploadedFile);
				assessment.setMark(mark);
				

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		User user = ((UserPrincipal) authentication.getPrincipal()).getUser();
		
		
		return user;
	}
}
