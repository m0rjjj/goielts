package au.com.goielts.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.com.goielts.core.UserPrincipal;
import au.com.goielts.model.Course;
import au.com.goielts.model.Student;
import au.com.goielts.model.Task;
import au.com.goielts.model.Teacher;
import au.com.goielts.model.User;
import au.com.goielts.services.CourseService;
import au.com.goielts.services.StudentService;
import au.com.goielts.services.TaskService;
import au.com.goielts.services.TeacherService;

@Controller
public class CourseController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TaskService taskService;

	@RequestMapping(value = "/course/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Authentication authentication) {
		if(request.isUserInRole("STUDENT")){
			return "forward:/course/index_student";
		}else if(request.isUserInRole("TEACHER")){
			return "forward:/course/list";
		}else{
			return "forward:/accessDenied";
		}
	}
	
	@RequestMapping(value = "/course/index_student", method = RequestMethod.GET)
	public String indexStudent(HttpServletRequest request){
		if(!request.isUserInRole("STUDENT")){
			return "forward:/accessDenied";
		}
		
		
		User user = getAuthenticatedUser();
		int courcesCount = courseService.countByStudentId(user.getId());
		
		if(courcesCount==0){
			return "forward:/course/no_course";
		}else if(courcesCount==1){
			Student student = studentService.with("courses").findById(user.getId());
			Set<Course> cources = student.getCourses();
			
			int id = cources.iterator().next().getId();
			
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
		model.addAttribute("tasks",getTasks(id));
		Course course = courseService.with("weeks").findById(id);
		model.addAttribute(course);
		return "/course/view";
	}
	
	@RequestMapping(value = "/course/list", method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		User user = getAuthenticatedUser();
		Set<Course> courses = null;

		if(request.isUserInRole("STUDENT")){
			Student student = studentService.with("courses").findById(user.getId());
			courses = student.getCourses();
			model.addAttribute("courses",courses);
			return "/course/list";
		}else if(request.isUserInRole("TEACHER")){
			Teacher teacher = teacherService.with("courses").findById(user.getId());
			courses = teacher.getCourses();
			model.addAttribute("courses",courses);
			return "/teacher/course/list";
		}else{
			return "/accessDenied";
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
	
	
	private List<Task> getTasks(int courseId) {
		List<Task> tasks = taskService.findAllByCourseId(courseId);
		return tasks;
	}

//	private String getPrincipal() {
//		String userName = null;
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null) {
//			return null;
//		}
//		Object principal = authentication.getPrincipal();
//
//		if (principal instanceof UserDetails) {
//			userName = ((UserDetails) principal).getUsername();
//		} else {
//			userName = principal.toString();
//		}
//		return userName;
//	}
}
