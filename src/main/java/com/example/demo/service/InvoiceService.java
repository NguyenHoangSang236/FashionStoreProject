package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Invoice;

@Service
public interface InvoiceService {
    List<Invoice> getInvoiceList();
}
