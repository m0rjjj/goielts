package au.com.goielts.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import au.com.goielts.widgets.Alert;

@Configuration
public class WidgetConfiguration {
	
	@Bean(name="alertWidget")
	public Alert alertWidget(){
		Alert alert = new Alert();
		alert.setType("warning");
		alert.setMessage("whoooooho");
		return alert;
	}
	
}
