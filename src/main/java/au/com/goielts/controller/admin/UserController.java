package au.com.goielts.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.com.goielts.JsonResponse;
import au.com.goielts.model.User;
import au.com.goielts.services.UserService;

@Controller
public class UserController {
	
	@Autowired
    private UserService userService;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(){
		return "/admin/index";
	}
	
	/* Users */
	
	@RequestMapping(value = "/admin/user_list", method = RequestMethod.GET)
	public String userList(ModelMap model){
		List<User> userList = userService.findAll();
		model.addAttribute("userList", userList);
		return "/admin/user_list";
	}
	
	@RequestMapping(value = "/admin/user_create", method = RequestMethod.GET)
	public String userCreate(ModelMap model){
		User user = new User();
		model.addAttribute("user", user);
		return "/admin/user_create";
	}
	
	@RequestMapping(value = { "/admin/user_create" }, method = RequestMethod.POST)
    public String userCreate(@Validated(User.ValidationStepOne.class) User user,  Errors errors,
            ModelMap model, RedirectAttributes redirectAttrs) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "/admin/user_create";
        }
		else{
			userService.save(user);
			redirectAttrs.addFlashAttribute("success", "User " + user.getFirstName()  + " created successfully");
			return "redirect:/admin/user_list";
		}
		
    }
	
	@RequestMapping(value = "/admin/user_edit/{id}", method = RequestMethod.GET)
	public String userEdit(@PathVariable int id, ModelMap model){
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "/admin/user_edit";
	}
	
	@RequestMapping(value = { "/admin/user_edit/{id}" }, method = RequestMethod.POST)
    public String updateEmployee(@Validated(User.ValidationStepOne.class) User user,  Errors errors,
            ModelMap model, @PathVariable String id, RedirectAttributes redirectAttrs) {
		
		if (errors.hasErrors()) {
            System.out.println(errors);
            return "/admin/user_edit";
        }
		else{
			userService.updateInfo(user);
			redirectAttrs.addFlashAttribute("success", "User " + user.getFirstName()  + " updated successfully");
			return "redirect:/admin/user_list";
		}
		
    }
	
	@RequestMapping(value = { "/admin/user_delete/{id}" }, method = RequestMethod.POST)
    public @ResponseBody JsonResponse userDelete(@PathVariable int id) {
		if(userService.deleteById(id)>0){
			return JsonResponse.factory(true);
		}else{
			return JsonResponse.factory(false);
		}
	}
}
