package dksstylecmuh.cmuhprojet.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    //vue simple

    // public void sendEmail(String to, String subject, String body){
    //   try {
    //       SimpleMailMessage message =new SimpleMailMessage();
    //       message.setTo(to);
    //       message.setSubject(subject);
    //       message.setText(body);
    //       mailSender.send(message);
    //   }
    //    catch (Exception e) {
    //     System.err.println("Error sending email: " + e.getMessage());
    //     e.printStackTrace();
    //   }
    // }

    // vue html


    public void sendHtmlEmail(String to, String subject, String prenom) throws MessagingException{
        Context context = new Context();
        context.setVariable("prenom", prenom);
        String htmlBody = templateEngine.process("emailTemplate", context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);

        mailSender.send(message);
    }
}
