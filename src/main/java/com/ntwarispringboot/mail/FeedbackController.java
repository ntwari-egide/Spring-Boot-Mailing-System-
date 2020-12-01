package com.ntwarispringboot.mail;

import com.ntwarispringboot.mail.config.MailConfiguration;
import com.ntwarispringboot.mail.models.FeedBack;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.ValidationException;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private MailConfiguration mailConfiguration;

    public FeedbackController(MailConfiguration mailConfiguration) {
        this.mailConfiguration = mailConfiguration;
    }

    @PostMapping("/")
    public void sendFeedback(@RequestBody FeedBack feedBack, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors())
            throw new ValidationException("Feedback is not valid");

        //creating the mail sender

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailConfiguration.getHost());
        mailSender.setPort(mailConfiguration.getPort());
        mailSender.setUsername(mailConfiguration.getUsername());
        mailConfiguration.setPassword(mailConfiguration.getPassword());

        //create an email instance

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(feedBack.getEmail());
        mailMessage.setTo("ntwarivalidation@java.com");
        mailMessage.setSubject("New Image sent in java from "+feedBack.getName());
        mailMessage.setText("My new java learning form tutorial teach java primes youtube tutorial");

        // send the mail

        mailSender.send(mailMessage);

    }
}
