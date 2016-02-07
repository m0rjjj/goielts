package au.com.goielts.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import au.com.goielts.core.FileHelper;


/**
 * The Class HelperConfiguration.
 */
@Configuration
public class HelperConfiguration {

	@Bean(name="fileHelper")
	public FileHelper dateHelper(){
		return new FileHelper();
	}
}
