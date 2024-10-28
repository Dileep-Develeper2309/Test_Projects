package com.dileep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dileep.dto.ContactForm;

@Controller
public class ContactController {
	@Autowired
    private JavaMailSender mailSender;

    @GetMapping("/contact")
    public String showForm(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }

    @PostMapping("/submitForm")
    public String submitForm(@ModelAttribute("contactForm") ContactForm contactForm) {
        sendEmail(contactForm);
        return "success";
    }

    private void sendEmail(ContactForm contactForm) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(contactForm.getEmail()); // Send email to the user who submitted the form
        message.setSubject("Thank you for contacting us");
        message.setText("Dear " + contactForm.getName() + ",\n\nThank you for your message:\n" + contactForm.getMessage());

        mailSender.send(message);
    }
}