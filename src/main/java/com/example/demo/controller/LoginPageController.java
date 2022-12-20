package com.example.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Staff;
import com.example.demo.entity.dto.LoginPage;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CartRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.StaffRepository;
import com.example.demo.service.EmailService;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.LoginState;
import com.example.demo.util.ValueRender;


@Controller
public class LoginPageController {
	@Autowired
	AccountRepository accRepo;
	
	@Autowired
	CustomerRepository cusRepo;
	
	@Autowired
	StaffRepository staffRepo;
	
	@Autowired 
	EmailService emailService;
	
	@Autowired
	CartRepository cartRepo;
	
	LoginPage loginPage = new LoginPage();
	String message;
	
	
	@GetMapping("/loginpage")
	public String showLoginPage(Model model) {
		model.addAttribute("loginPage", loginPage);
		return "login";
	}
	 
	
	@PostMapping("/loginpage")
	public String submitForm(Model model, HttpSession session, @ModelAttribute("loginPage") LoginPage loginPage, @RequestParam(value="action") String action,  HttpServletResponse response) { 
		if(action.equals("login")) {
		    //username and password are null
		    if(loginPage.getLoginUserName() == null && loginPage.getLoginPassword() == null) {
	        	GlobalStaticValues.message = "Please input your Account User name and Password !!";
	        	message = GlobalStaticValues.message;
	        	model.addAttribute("message", message);
	        	
	        }
		    //password is null
	        else if(loginPage.getLoginUserName() != null && loginPage.getLoginPassword() == null) {
	        	GlobalStaticValues.message = "Please input Password !!";
	        	message = GlobalStaticValues.message;
	        	model.addAttribute("message", message);
	        }
		    //username is null
	        else if(loginPage.getLoginUserName() == null && loginPage.getLoginPassword() != null) {
	        	GlobalStaticValues.message = "Please input Account User name !!";
	        	model.addAttribute("message", GlobalStaticValues.message);
	        }
		    //account existed --> proceed login
	        else {
			    Account acc = accRepo.findByUserNameAndPassword(loginPage.getLoginUserName(), loginPage.getLoginPassword());

			    if(acc != null) {
			    	Cookie cookie = new Cookie(acc.getUserName(), Integer.toString(acc.getId()));
		            cookie.setMaxAge(7 * 24 * 60 * 60);
		            response.addCookie(cookie);
		            session.setAttribute("currentuser", acc);
		            
		            LoginState.currentAccount = acc;
		            
		            if(acc.getRole().equals("admin")) {
		                GlobalStaticValues.currentStaff = acc.getStaff();
		                GlobalStaticValues.currentPage = "/allproduct";
		                return "redirect:" + GlobalStaticValues.currentPage;
		            }
		            else if(acc.getRole().equals("user")) {
		            	if(acc.getStatus().equals("banned")) {
		            		GlobalStaticValues.message = "This account has been banned !!";
					    	message = GlobalStaticValues.message;
				        	System.out.println(message);
				        	model.addAttribute("message", message);
		            	}
		            	else {
//		            		System.out.println(ValueRender.convertByteToString(acc.getCustomer().getImage()));
		            		GlobalStaticValues.currentCustomer = acc.getCustomer();
		            		GlobalStaticValues.customerFullCartIdList = cartRepo.getFullCartIdListByCustomerId(acc.getCustomer().getId());
		            		
		            		return "redirect:" + GlobalStaticValues.currentPage;
		            	}
		            }
			    }
			    //account is not existed, username is not null
			    else {
			    	GlobalStaticValues.message = "User name or password is invalid !!";
			    	message = GlobalStaticValues.message;
		        	System.out.println(message);
		        	model.addAttribute("message", message);
		        	
		        	return "login";
			    }
	        }
		}
		
		//click Register
		if(action.equals("register")) {
		    Account acc = accRepo.findByUserName(loginPage.getRegisterUserName());

		    if(acc == null) {
	            Account newAcc = new Account(loginPage.getRegisterUserName(), loginPage.getRegisterPassword(), "user");
	            accRepo.save(newAcc);
	            
	            Customer newCus = new Customer(loginPage.getFullName(), loginPage.getEmail(), loginPage.getPhoneNumber(), GlobalStaticValues.defaultUserImageByte, newAcc);
	            cusRepo.save(newCus);
	            
	            return "login";
	        }
	        //account existed --> notice 'This user name has already existed !!'
	        else {
	        	GlobalStaticValues.message = "This user name has already existed !!";
	        	message = GlobalStaticValues.message;
	        	System.out.println(message);
	        	model.addAttribute("message", message);
	            return "login";
	        }
		}		
		
		//click Forget password
		if(action.equals("forgot")) {
		    //username is null
	        if(loginPage.getLoginUserName() == null) {
	        	GlobalStaticValues.message = "Please input Account User name !!";
	        }
		    //account existed --> notice 'Check your email for your temporary password !!'
	        else {
				Account acc = accRepo.findByUserName(loginPage.getLoginUserName());

				if(acc != null) {
					emailService.forgotPassword(loginPage.getLoginUserName());
					
					return "login";
				}
				//account not existed --> notice 'This user not existed !!'
				else {
					return "login";
				}
	        }
		}
		
		return "login";
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session ) {
	    session.invalidate();
	    GlobalStaticValues.currentCustomer = new Customer();
	    GlobalStaticValues.currentStaff = new Staff();
	    GlobalStaticValues.currentPage = "/home";
	    return "redirect:/loginpage";
	} 
}
