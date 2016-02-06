package au.com.goielts.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import au.com.goielts.widgets.Alert;

/**
 * The Class WidgetConfiguration.
 */
@Configuration
public class WidgetConfiguration {
	
	/**
	 * Alert widget.
	 *
	 * @return the alert
	 */
	@Bean(name="alertWidget")
	public Alert alertWidget(){
		Alert alert = new Alert();
		alert.setType("warning");
		alert.setMessage("whoooooho");
		return alert;
	}
	
}
