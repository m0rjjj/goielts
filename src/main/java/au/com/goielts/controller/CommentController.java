package au.com.goielts.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import au.com.goielts.JsonResponse;
import au.com.goielts.core.UserPrincipal;
import au.com.goielts.model.Comment;
import au.com.goielts.model.User;
import au.com.goielts.model.Week;
import au.com.goielts.services.CommentService;
import au.com.goielts.services.UserService;
import au.com.goielts.services.WeekService;

@Controller
public class CommentController {
	
	@Autowired WeekService weekService;
	@Autowired UserService userService;
	@Autowired CommentService commentService;

	@RequestMapping(value = "/comment/add", method = RequestMethod.POST)
	@ResponseBody public JsonResponse add( 
			@RequestParam(value="message", required=false) String message,
			@RequestParam(value="weekId", required=false) int weekId
			){
		System.out.println(weekId+ " " +message);
		Comment comment = new Comment();
		comment.setMessage(message);
		
		User user = userService.findById(getAuthenticatedUser().getId());
		comment.setUser(user);
		
		commentService.save(comment);
		
		Week week = weekService.findById(weekId);
		Set<Comment> comments = week.getComments();
		comments.add(comment);
		week.setComments(comments);
		
		weekService.merge(week);
		
		Map<String, Object> data = new HashMap<>();
		data.put("message", message);
		data.put("user", user.getFirstName()+" "+user.getLastName());
		JsonResponse response = JsonResponse.factory(true, data);
		
		return response;
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
}
