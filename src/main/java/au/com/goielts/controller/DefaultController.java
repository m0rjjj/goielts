package au.com.goielts.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.com.goielts.configuration.CustomSuccessHandler;
import au.com.goielts.dao.StudentProfileDao;
import au.com.goielts.model.StudentProfile;
import au.com.goielts.model.User;
import au.com.goielts.services.StudentProfileService;
import au.com.goielts.services.UserService;


@Controller
public class DefaultController {

    @Autowired
    CustomSuccessHandler handler;
    
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public String demo(){
    	return "demo";
    }
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return "forward:"+handler.determineTargetUrl(authentication);
    }
 
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }
 
    @Autowired
    UserService service;
    
    @Autowired
    StudentProfileService sService;
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(ModelMap model) {
    	
    	User user= service.findById(1);
    	System.out.println(user.getStudentProfile());
    	
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
 
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
    
    @RequestMapping(value="/error", method = RequestMethod.GET)
    public String error () {
        
        return "error";
    }
}
