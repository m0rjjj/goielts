package au.com.goielts.controller.teacher;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.com.goielts.JsonResponse;
import au.com.goielts.core.UserPrincipal;
import au.com.goielts.model.UploadedFile;
import au.com.goielts.model.User;
import au.com.goielts.model.Week;
import au.com.goielts.services.UploadedFileService;
import au.com.goielts.services.WeekService;

@Controller
public class TeacherMaterialController {
	
	@Autowired WeekService weekService;
	
	@Autowired Environment environment;
	
	@Autowired UploadedFileService uploadedFileService;
	
	@RequestMapping(value = "/teacher/material/edit/week/{weekId}", method = RequestMethod.GET)
	public String viewMaterial(Model model, @PathVariable int weekId, HttpServletRequest request) {
		Week week = weekService.findById(weekId);
		model.addAttribute("week", week);
		
		return "/teacher/material/edit";
	}

	@RequestMapping(value = "/teacher/material/edit/week/{weekId}", method = RequestMethod.POST)
	public String handleFileUpload(Model model, RedirectAttributes redirectAttrs, @RequestParam("file") MultipartFile file, @PathVariable int weekId,
			HttpServletRequest request) {
		if (!file.isEmpty()) {
			try {
				Week week = weekService.findById(weekId);
				// saving the file
				byte[] bytes = file.getBytes();
				String filePath = String.format("/files/materials/course_%d/week_%d/user_%d/%s", 
						week.getCourse().getId(),
						week.getId(), 
						getAuthenticatedUser().getId(),
						file.getOriginalFilename());
				String realPath = environment.getProperty("serverPath") + filePath;
				File destination = new File(realPath);
				destination.getParentFile().mkdirs();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(destination, false));
				stream.write(bytes);
				stream.close();
				
				UploadedFile uploadedFile = new UploadedFile();
				// populating fields
				uploadedFile.setFileName(file.getOriginalFilename());
				uploadedFile.setPath(filePath);
				uploadedFile.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
				uploadedFile.setSize(file.getSize());
				
				uploadedFileService.save(uploadedFile);


				Set<UploadedFile> materials = week.getMaterials();
				materials.add(uploadedFile);
				week.setMaterials(materials);
				weekService.merge(week);
				
				redirectAttrs.addFlashAttribute("success", "File " + file.getOriginalFilename()  + " uploaded successfully");
				
				return "redirect:/teacher/material/edit/week/{weekId}";
			} catch (Exception e) {
				System.out.println("You failed to upload the file => " + e.getMessage());
				return "redirect:/teacher/material/edit/week/"+weekId;
			}
		} else {
			model.addAttribute("message", "You failed to upload the file, because the file was empty.");
			return "redirect:/teacher/material/edit/week/" + weekId;
		}
	}
	
	@RequestMapping(value = { "/teacher/material/delete/{id}" }, method = RequestMethod.POST)
    public @ResponseBody JsonResponse userDelete(@PathVariable int id) {
		if(uploadedFileService.deleteById(id)>0){
			return JsonResponse.factory(true);
		}else{
			return JsonResponse.factory(false);
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
