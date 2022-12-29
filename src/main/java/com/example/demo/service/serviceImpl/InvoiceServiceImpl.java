package com.example.demo.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Invoice;
import com.example.demo.entity.Product;
import com.example.demo.entity.dto.InvoicesWithProducts;
import com.example.demo.respository.InvoiceRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	InvoiceRepository invoiceRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	
	@Override
	public void cancelOrder(int invoiceId) {
		Invoice invoice = invoiceRepo.getInvoiceById(invoiceId);
		List<InvoicesWithProducts> invoicesWithProductsList = invoice.getInvoicesWithProducts();
		
		for(int i = 0; i < invoicesWithProductsList.size(); i++) {
			int quantity = invoicesWithProductsList.get(i).getQuantity();
			Product product = invoicesWithProductsList.get(i).getProduct();
//			int beforeQuant = product.getAvailableQuantity();
			product.addQuantity(quantity);
			
//			System.out.println(product.getId() + ": " + beforeQuant + " + " + quantity +"->"+product.getAvailableQuantity());
			productRepo.save(product);
		}
	}
}
