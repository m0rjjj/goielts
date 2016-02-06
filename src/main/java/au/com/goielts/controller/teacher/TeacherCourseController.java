package au.com.goielts.controller.teacher;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.com.goielts.model.Course;
import au.com.goielts.services.CourseService;

@Controller
public class TeacherCourseController {
	
	@Autowired
	private CourseService courseService;
	
	@RequestMapping(value = "/teacher/course/edit/{id}", method = RequestMethod.GET)
	public String courseEdit(@PathVariable int id, ModelMap model){
		Course course = courseService.findById(id);
		model.addAttribute("course", course);
		return "/teacher/course/edit";
	}
	
	@RequestMapping(value = "/teacher/course/edit/{id}" , method = RequestMethod.POST)
    public String courseEdit(@Valid Course course,  Errors errors,
            ModelMap model, @PathVariable String id, RedirectAttributes redirectAttrs) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "/teacher/course/edit";
        }
		else{
			courseService.update(course);
			redirectAttrs.addFlashAttribute("success", "Course " + course.getName()  + " updated successfully");
			return "redirect:/course/list";
		}
		
    }
	
	
}
