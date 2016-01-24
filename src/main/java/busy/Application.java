package busy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring Boot Application class. It runs the whole application with an embedded
 * web container, and enables auto configuration and dependency injection.
 * 
 * @author malkomich
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"busy"})
public class Application extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Injection of the message properties files, so internationalization is
	 * enabled.
	 * 
	 * @return
	 */
	@Bean(name = "messageSource")
	@Scope(value="singleton")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages/messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}
	
	@Bean(name = "validator")
	public LocalValidatorFactoryBean validator()
	{
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}
	
	@Override
	public Validator getValidator()
	{
	    return validator();
	}
}
