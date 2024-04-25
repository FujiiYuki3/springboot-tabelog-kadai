package com.example.tabelogkadai.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.tabelogkadai.entity.User;
import com.example.tabelogkadai.service.VerificationTokenService;

@Component
public class ResetEventListener {
	private final VerificationTokenService verificationTokenService;
	private final JavaMailSender javaMailSender;
	
	public ResetEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
		this.verificationTokenService = verificationTokenService;
		this.javaMailSender = mailSender;
	}
	
	@EventListener
	private void onResetEvent(ResetEvent resetEvent) {
		User user = resetEvent.getUser();
		String token = UUID.randomUUID().toString();
		verificationTokenService.update(user, token);
		
		String recipientAddress = user.getEmail();
		String subject = "メール認証";
		String confirmationUrl = resetEvent.getRequestUrl() + "/verify?token=" + token;
		String message = "以下のリンクをクリックしてパスワードをリセットしてください。";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(recipientAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(message + "\n" + confirmationUrl);
		javaMailSender.send(mailMessage);
	}


}
