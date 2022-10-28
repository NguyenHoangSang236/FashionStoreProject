package com.example.demo.util;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Staff;

public class LoginState {
    public static Account currentAccount;
    
    public static Staff currentStaff = new Staff();
    
    public static Customer currentCustomer = new Customer();
}
