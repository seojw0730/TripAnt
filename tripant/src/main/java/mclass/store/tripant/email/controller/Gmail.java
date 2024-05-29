package mclass.store.tripant.email.controller;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@PropertySource("classpath:/keyproperties/apikeys.properties")
@Component
public class Gmail extends Authenticator {
	
	@Value("${email.username}")
	private String email;
	@Value("${email.password}")
	private String password;
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(email, password);
	}
}
