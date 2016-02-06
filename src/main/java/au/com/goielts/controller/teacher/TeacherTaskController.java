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
import au.com.goielts.model.Task;
import au.com.goielts.services.CourseService;
import au.com.goielts.services.TaskService;

@Controller
public class TeacherTaskController {

	@Autowired TaskService taskService;
	@Autowired CourseService courseService;
	
	@RequestMapping(value = "/teacher/task/edit/{id}", method = RequestMethod.GET)
	public String edit(Model model, @PathVariable int id) {
		
		Task task = taskService.findById(id);
		model.addAttribute("task", task);
		
		return "/teacher/task/edit";
	}
	
	@RequestMapping(value = { "/teacher/task/edit/{id}" }, method = RequestMethod.POST)
    public String userCreate(@Valid Task task,  Errors errors,
            ModelMap model, RedirectAttributes redirectAttrs, @PathVariable int id) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "/teacher/task/edit";
        }
		else{
			taskService.update(task);
			redirectAttrs.addFlashAttribute("success", "Task " + task.getName()  + " updated successfully");
			return "redirect:/task/view/"+id;
		}
		
    }
	
	@RequestMapping(value = "/teacher/task/new/course/{courseId}", method = RequestMethod.GET)
	public String newTask(Model model, @PathVariable int courseId) {
		Task task = new Task();
		model.addAttribute("task", task);
		System.out.println(task);
		
		return "/teacher/task/new";
	}
	
	@RequestMapping(value = { "/teacher/task/new/course/{courseId}" }, method = RequestMethod.POST)
    public String newTask(@Valid Task task,  Errors errors,
            ModelMap model, RedirectAttributes redirectAttrs, @PathVariable int courseId) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "/teacher/task/new";
        }
		else{
			Course course = courseService.findById(courseId);
			task.setCourse(course);

			taskService.save(task);
			
			redirectAttrs.addFlashAttribute("success", "Task " + task.getName()  + " updated successfully");
			return "redirect:/task/view/"+task.getId();
		}
		
    }
	
	@RequestMapping(value = { "/teacher/task/delete/{id}" }, method = RequestMethod.GET)
    public String taskDelete(@PathVariable int id, ModelMap model) {
		Task task = taskService.findById(id);
		model.addAttribute("task", task);
		
		return "/teacher/task/delete";
	}
	
	@RequestMapping(value = { "/teacher/task/delete/{id}/confirm/{confirm}" }, method = RequestMethod.GET)
    public String taskDeleteConfirm(@PathVariable int id,@PathVariable boolean confirm, ModelMap model) {
		Task task = taskService.findById(id);
		if(task!=null){
			if(confirm){
				taskService.delete(id);
			}
			return "redirect:/course/view/"+task.getCourse().getId();
		}
		return "redirect:course/list";
		
	}
}
