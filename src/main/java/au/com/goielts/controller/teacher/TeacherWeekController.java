/*
 * 
 */
package au.com.goielts.controller.teacher;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.com.goielts.model.Course;
import au.com.goielts.model.Week;
import au.com.goielts.services.CourseService;
import au.com.goielts.services.WeekService;

/**
 * The Class TeacherWeekController.
 */
@Controller
public class TeacherWeekController {
	
	/** The course service. */
	@Autowired CourseService courseService;
	
	/** The week service. */
	@Autowired WeekService weekService;
	

	/**
	 * Edits the week.
	 *
	 * @param model the model
	 * @param id the id
	 * @return the string
	 */
	@RequestMapping(value = "/teacher/week/edit/{id}", method = RequestMethod.GET)
	public String editWeek(Model model, @PathVariable int id) {
		
		Week week = weekService.findById(id);
		model.addAttribute("week", week);
		
		return "/teacher/week/edit";
	}
	

	/**
	 * Edits the week.
	 *
	 * @param week the week
	 * @param errors the errors
	 * @param model the model
	 * @param redirectAttrs the redirect attrs
	 * @param id the id
	 * @return the string
	 */
	@RequestMapping(value = { "/teacher/week/edit/{id}" }, method = RequestMethod.POST)
    public String editWeek(@Valid Week week,  Errors errors,
            ModelMap model, RedirectAttributes redirectAttrs, @PathVariable int id) {
		int courseId = weekService.findById(id).getCourse().getId();
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "/teacher/week/edit";
        }
		else{
			weekService.update(week);
			redirectAttrs.addFlashAttribute("success", "Week " + week.getNumber()  + " updated successfully");
			return "redirect:/course/view/"+courseId;
		}
    }
	

	/**
	 * New week.
	 *
	 * @param model the model
	 * @param courseId the course id
	 * @return the string
	 */
	@RequestMapping(value = "/teacher/week/new/course/{courseId}", method = RequestMethod.GET)
	public String newWeek(Model model, @PathVariable int courseId) {
		Week week = new Week();
		Course course = courseService.findById(courseId);
		model.addAttribute("week", week);
		model.addAttribute("course", course);
		System.out.println(course);
		
		return "/teacher/week/new";
	}
	

	/**
	 * New week.
	 *
	 * @param task the task
	 * @param errors the errors
	 * @param model the model
	 * @param redirectAttrs the redirect attrs
	 * @param courseId the course id
	 * @return the string
	 */
	@RequestMapping(value = { "/teacher/week/new/course/{courseId}" }, method = RequestMethod.POST)
    public String newWeek(@Valid Week week,  Errors errors,
            ModelMap model, RedirectAttributes redirectAttrs, @PathVariable int courseId) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            Course course = courseService.findById(courseId);
    		model.addAttribute("course", course);
            return "/teacher/week/new";
        }
		else{
			Course course = courseService.findById(courseId);
			week.setCourse(course);

			weekService.save(week);
			
			redirectAttrs.addFlashAttribute("success", "Week #" + week.getNumber()  + " saved successfully");
			return "redirect:/course/view/"+courseId;
		}
		
    }
	

	/**
	 * Delete week.
	 *
	 * @param id the id
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = { "/teacher/week/delete/{id}" }, method = RequestMethod.GET)
    public String deleteWeek(@PathVariable int id, ModelMap model) {
		Week week = weekService.findById(id);
		model.addAttribute("week", week);
		
		return "/teacher/week/delete";
	}
	

	/**
	 * Delete week confirm.
	 *
	 * @param id the id
	 * @param confirm the confirm
	 * @param model the model
	 * @return the string
	 */
	@RequestMapping(value = { "/teacher/week/delete/{id}/confirm/{confirm}" }, method = RequestMethod.GET)
    public String deleteWeekConfirm(@PathVariable int id,@PathVariable boolean confirm, ModelMap model, RedirectAttributes redirectAttrs) {
		Week week = weekService.findById(id);
		if(week!=null){
			if(confirm){
				weekService.delete(id);
			}
			redirectAttrs.addFlashAttribute("success", "Week #" + week.getNumber()  + " deleted successfully");
			return "redirect:/course/view/"+week.getCourse().getId();
		}
		return "redirect:course/list";
		
	}
}
