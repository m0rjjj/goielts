package au.com.goielts.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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

import au.com.goielts.model.Course;
import au.com.goielts.model.User;
import au.com.goielts.services.CourseService;
import au.com.goielts.services.UserService;

@Controller
public class CourseController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private CourseService courseService;

	@RequestMapping(value = "/course/index", method = RequestMethod.GET)
	public String index() {
		Set<Course> courses = userService.with("courses").findByEmail(getPrincipal()).getCourses();
		if(courses.size()==0){
			return "forward:/course/no_course";
		}else if(courses.size()==1){
			int id = courses.iterator().next().getId();
			return "forward:/course/view/"+id;
		}else {
			return "forward:/course/list";
		}
	}
	
	@RequestMapping(value = "/course/no_course", method = RequestMethod.GET)
	public String noCourse() {
		return "/course/no_course";
	}
	
	@RequestMapping(value = "/course/view/{id}", method = RequestMethod.GET)
	public String view(@PathVariable int id, Model model) {
		Course course = courseService.findById(id);
		model.addAttribute(course);
		return "/course/view";
	}
	
	@RequestMapping(value = "/course/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		Set<Course> courses = userService.with("courses").findByEmail(getPrincipal()).getCourses();
		/*if(request.isUserInRole("STUDENT")){
			
		}else if(request.isUserInRole("TEACHER")){
			
		}*/
		
		model.addAttribute("courses",courses);
		return "/course/list";
		
	}
	
	@ModelAttribute("authUser")
	private User getAuthenticatedUser() {
		return userService.findByEmail(getPrincipal());
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
