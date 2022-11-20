package com.example.demo.service.serviceImpl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.respository.AccountRepository;
import com.example.demo.service.EmailService;
import com.example.demo.util.Network;

@Service
public class EmailServiceImpl implements EmailService{
	@Autowired
    JavaMailSender mailSender;
	
	@Autowired
	AccountRepository accountRepo;
	

	@Override
	public void forgotPassword(String user) {
		Customer currentCustomer = accountRepo.findByUserName(user).getCustomer();
		
		String toAddress = currentCustomer.getEmail();
	    String fromAddress = "nguyenhoangsang236@gmail.com";
	    String senderName = "Fool!sh Fashion Store";
	    String subject = "Your new temporary password";
	    String content = "Dear [[name]],<br>"
	            + "Your new temporary password is " + Network.randomTemporaryPassword(user) + "<br>"
	            + "Please rememder to change a new password for your new account because this temporary password will be changed after you close the website !!<br><br>"
	            + "Thank you,<br>"
	            + "Fool!sh Fashion Store";
	     
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	     
	    try {
			helper.setFrom(fromAddress, senderName);helper.setTo(toAddress);
		    helper.setSubject(subject);
		     
		    content = content.replace("[[name]]", currentCustomer.getName());
		     
		    helper.setText(content, true);
		} 
	    catch (Exception e) {
			e.printStackTrace();
		}
	    
	    mailSender.send(message);
	}
}
