package au.com.goielts.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.com.goielts.configuration.CustomSuccessHandler;
import au.com.goielts.core.UserPrincipal;
import au.com.goielts.model.Student;
import au.com.goielts.model.Teacher;
import au.com.goielts.model.User;
import au.com.goielts.services.StudentService;
import au.com.goielts.services.TeacherService;


@Controller
public class DefaultController {

    @Autowired
    CustomSuccessHandler handler;
    @Autowired StudentService studentService;
    @Autowired TeacherService teacherService;
    
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody public String demo(){
    	
    	return "demo";
    }
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("forward:"+handler.determineTargetUrl(authentication));
		return "forward:"+handler.determineTargetUrl(authentication);
    }
 
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        return "accessDenied";
    }
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap model, HttpServletRequest request) {
    	if(request.isUserInRole("STUDENT")){
    		Student profile = studentService.findById(getAuthenticatedUser().getId());
    		model.addAttribute("student", profile);
    		return "profile_student";
    	}else if(request.isUserInRole("TEACHER")){
    		Teacher profile = teacherService.findById(getAuthenticatedUser().getId());
    		model.addAttribute("teacher",profile);
    		return "profile_teacher";
    	}
        return "profile";
    }
    
    @RequestMapping(value = { "/profile_student" }, method = RequestMethod.POST)
    public String updateStudentProfile(@Validated(User.ValidationStudentProfile.class) Student profile,  Errors errors,
            ModelMap model, RedirectAttributes redirectAttrs) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "profile_student";
        }
		else{
			studentService.updateProfile(getAuthenticatedUser().getId(), profile);
			
			redirectAttrs.addFlashAttribute("success", "Profile was updated successfully");
			return "redirect:/profile";
		}
		
    }
    
    @RequestMapping(value = { "/profile_teacher" }, method = RequestMethod.POST)
    public String updateTeacherProfile(@Validated(User.ValidationTeacherProfile.class) Teacher teacher,  Errors errors,
            ModelMap model, RedirectAttributes redirectAttrs) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "profile_teacher";
        }
		else{
			teacherService.updateProfile(getAuthenticatedUser().getId(), teacher);
			
			redirectAttrs.addFlashAttribute("success", "Profile was updated successfully");
			return "redirect:/profile";
		}
		
    }
 
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(ModelMap model) {
    	
        return "login";
    }
 
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
 
	private User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		User user = ((UserPrincipal) authentication.getPrincipal()).getUser();
		
		
		return user;
	}
    
    @RequestMapping(value="/error", method = RequestMethod.GET)
    public String error () {
        
        return "error";
    }
    
}
