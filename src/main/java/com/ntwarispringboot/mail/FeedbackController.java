package com.ntwarispringboot.mail;

import com.ntwarispringboot.mail.config.MailConfiguration;
import com.ntwarispringboot.mail.models.Message;
import com.ntwarispringboot.mail.models.MessageAttachment;
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
import java.time.LocalDate;

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

    public String sendHtmlContentEmail(String name,String smallContent){
        return "<h1 style='font-family: Manrope;'>Hello "+name+", welcome back</h2>"+
                "<p>This is the simple way of testing html in <b>Java</b> , by accessing the emails from many sources on enternet</p>"+
                "<p style='font-size: 23px;'>Your verification code is 239392 </p>"+
                "<p style='font-size: 17px;'>Small content <br /> "+smallContent+" </p>"+
                "<button>Visit Site</button>";
    }

    @PostMapping("/attachment")
    public String sendingEmailAndAttachment(@RequestBody MessageAttachment messageAttachment) throws Exception{
        MimeMessage mailMessage = mailSender.createMimeMessage();

//         sending the attachment
//         tre = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true);
        helper.setTo(messageAttachment.getRecieverEmail());
        helper.setSubject(messageAttachment.getSubject());
//        helper.setText(messageAttachment.getContent());

        helper.setText(sendHtmlContentEmail(messageAttachment.getRecieverEmail(),messageAttachment.getContent()),true);

        FileSystemResource file = new FileSystemResource(new File(String.valueOf(messageAttachment.getAttachment())));

        helper.addAttachment(messageAttachment.getAttachment().getName() + " - "+ LocalDate.now()+".png", file);

        // send the mail

        mailSender.send(mailMessage);

        return"Email and attachment Sent";
    }
}
