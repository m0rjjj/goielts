package au.com.goielts.controller.admin;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.com.goielts.model.Course;
import au.com.goielts.model.Week;
import au.com.goielts.services.CourseService;

@Controller
public class AdminCourseController {
	
	@Autowired
    private CourseService courseService;
	
	@RequestMapping(value = "/admin/course_list", method = RequestMethod.GET)
	public String courseList(ModelMap model){
		List<Course> courseList = courseService.findAll();
		System.out.println(courseList);
		model.addAttribute("courseList", courseList);
		return "/admin/course_list";
	}
	
	@RequestMapping(value = "/admin/get_course/{id}", method = RequestMethod.GET)
	public @ResponseBody Set<Week> getCourse(@PathVariable int id){
		return courseService.findById(id).getWeeks();
	}
	
	@RequestMapping(value = "/admin/course_edit/{id}", method = RequestMethod.GET)
	public String userEdit(@PathVariable int id, ModelMap model){
		Course course = courseService.findById(id);
		model.addAttribute("course", course);
		return "/admin/course_edit";
	}
	
	@RequestMapping(value = { "/admin/course_edit/{id}" }, method = RequestMethod.POST)
    public String updateEmployee(@Valid Course course,  Errors errors,
            ModelMap model, @PathVariable String id, RedirectAttributes redirectAttrs) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "/admin/course_edit";
        }
		else{
			courseService.update(course);
			redirectAttrs.addFlashAttribute("success", "Course " + course.getName()  + " updated successfully");
			return "redirect:/admin/course_list";
		}
		
    }
}
