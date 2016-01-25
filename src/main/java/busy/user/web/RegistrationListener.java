package busy.user.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import busy.user.User;
import busy.user.UserService;

@Component

public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	private static final String SUBJECT_MSG = "verification.email.subject";
	private static final String CONTENT_MSG = "verification.email.content";
	private static final String VERIFICATION_PATH = "/verificate_email?token=";
	
	@Autowired
	private UserService service;

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;
	
	/**
	 * Absolute path of the root URL, to let from now the use of relative URL
	 * paths.
	 */
	@Value("${web.rootUrl}")
	private String rootUrl;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {

		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		service.createVerificationToken(user, token);
		String recipientAddress = user.getEmail();
		String subject = messages.getMessage(SUBJECT_MSG, null, event.getLocale());
		String confirmationUrl = event.getAppUrl() + VERIFICATION_PATH + token;
		String message = messages.getMessage(CONTENT_MSG, null, event.getLocale());
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + rootUrl + confirmationUrl);
		mailSender.send((SimpleMailMessage)email);
		
	}
}
