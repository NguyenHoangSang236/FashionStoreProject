package com.example.demo.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Staff;
import com.example.demo.respository.CustomerRepository;

public class LoginState {
    public static Account currentAccount;
    
    public static Staff currentStaff = new Staff();
    
    public static Customer currentCustomer = new Customer();
    
    public static boolean isLoggedIn(Model model, HttpServletRequest request, CustomerRepository cusRepo) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Customer accountDetail = cusRepo.getCustomerById(Integer.valueOf(cookies[0].getValue()));
            model.addAttribute("userpic", accountDetail.getImage());
            model.addAttribute("username", accountDetail.getName());
            model.addAttribute("useremail", accountDetail.getEmail());
            model.addAttribute("userphonenumber", accountDetail.getPhoneNumber());
            model.addAttribute("userid", accountDetail.getId());
            return true;
        }
        else {
            return false;
        }
    }
}
