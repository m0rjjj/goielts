package au.com.goielts.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import au.com.goielts.services.MessageByLocaleService;
import au.com.goielts.widgets.Alert;


@Controller
public class HomeController {
	
	@Autowired public Alert alert;
	
	@ModelAttribute ("alert")
	public Alert alert(){
		return alert;
	};
	
	@Autowired MessageByLocaleService msg;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public String sayHelloAgain(ModelMap model) {
		
		model.addAttribute("greeting", "Hello World Again, from Spring 4 MVC");
        
        return "home";
    }
}
