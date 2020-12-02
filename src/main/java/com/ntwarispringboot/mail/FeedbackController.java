package com.ntwarispringboot.mail;

import com.ntwarispringboot.mail.config.MailConfiguration;
import com.ntwarispringboot.mail.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import javax.xml.bind.ValidationException;
import java.io.File;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    private MailConfiguration mailConfiguration;

    public FeedbackController(MailConfiguration mailConfiguration) {
        this.mailConfiguration = mailConfiguration;
    }

    @Autowired
    private JavaMailSender mailSender;
    @PostMapping
    public String sendFeedback(@RequestBody Message message, BindingResult bindingResult) throws Exception {
        System.out.println(mailConfiguration.getHost());
        if(bindingResult.hasErrors())
            throw new ValidationException("Feedback is not valid");

        //create an email instance , working

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailConfiguration.getUsername());
        mailMessage.setTo(message.getRecieverEmail());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getContent());

        // send the mail

        mailSender.send(mailMessage);

        return "Email sent";

    }

    @PostMapping("/attachment")
    public String sendingEmailAndAttachment() throws Exception{
        MimeMessage mailMessage = mailSender.createMimeMessage();

//         sending the attachment
//         tre = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true);
        helper.setTo("ntwariegide2003@gmail.com");
        helper.setSubject("Email Attaching file");
        helper.setText(
                "<h1>Email Attachment : Yombi rwanda</h1>" +
                        "Enjoy the videos and music you love, upload original content, and share it all with friends, family, and the world on YouTube" +
                        "<button>Click me</button>");

        FileSystemResource file = new FileSystemResource(new File("C:/Users\\user\\Desktop\\shapes.png"));

        helper.addAttachment("attachment.png", file);

        // send the mail

        mailSender.send(mailMessage);
        return"Email and attachment Sent";
    }
}
