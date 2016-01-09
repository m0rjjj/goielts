package au.com.goielts.widgets;

import org.springframework.stereotype.Component;

@Component
public class Alert {
	private String type;
	private String message;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
