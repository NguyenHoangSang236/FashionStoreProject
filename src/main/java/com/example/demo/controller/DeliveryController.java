package com.example.demo.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Delivery;
import com.example.demo.entity.Invoice;
import com.example.demo.entity.Staff;
import com.example.demo.entity.dto.CheckoutInfo;
import com.example.demo.entity.dto.DeliveryReportInfo;
import com.example.demo.entity.dto.InvoicesWithProducts;
import com.example.demo.respository.AccountRepository;
import com.example.demo.respository.CatalogRepository;
import com.example.demo.respository.CustomerRepository;
import com.example.demo.respository.DeliveryRepository;
import com.example.demo.respository.InvoiceRepository;
import com.example.demo.respository.ProductRepository;
import com.example.demo.respository.StaffRepository;
import com.example.demo.service.ProductService;
import com.example.demo.util.GlobalStaticValues;
import com.example.demo.util.ValueRender;

@Controller
public class DeliveryController {
	 @Autowired
	 ProductRepository productRepo;

	 @Autowired
	 ProductService productService;
	    
	 @Autowired
	 CatalogRepository catalogRepo;
	    
	 @Autowired
	 CustomerRepository cusRepo;
	 
	 @Autowired
	 StaffRepository staffRepo;
	 
	 @Autowired
	 InvoiceRepository invoiceRepo;
	 
	 @Autowired
	 DeliveryRepository deliveryRepo;
	 
	 Customer customer = new Customer();
	 List<Invoice> packingCodInvoicesList;
	 List<Invoice> shipperInvoicesList;
	 Staff currrentShipper;
	 DeliveryReportInfo deliveryReportInfo = new DeliveryReportInfo();
	 Invoice currentInvoice = new Invoice();
	 Delivery currentDelivery = new Delivery();
	 
	
	 public String renderToShipperForms(HttpSession session, Model model, List<Invoice> invoiceList, String page) {
		 Account currentUser = (Account)session.getAttribute("currentuser");
			
			if(currentUser != null) {
				currrentShipper = staffRepo.getStaffByAccountId(currentUser.getId());
			    				
				model.addAttribute("invoiceList", invoiceList);
			    model.addAttribute("curentcusImage",currrentShipper.convertByteImamgeToBase64String());
			    model.addAttribute("curentcusName",currrentShipper.getName());
			    if(page.contains("delivery-process")) {
			    	return "delivery-process";
			    } else return "shipper-home";
			}
			else {
				GlobalStaticValues.currentPage = page;
				return "redirect:/loginpage";
			}
	 }
	 
	 
	 public String renderToDeliveryReportForm(HttpSession session, Model model, int deliId) {
		 Account currentUser = (Account)session.getAttribute("currentuser");
			
			if(currentUser != null) {
				Staff currrentShipper = staffRepo.getStaffByAccountId(currentUser.getId());
			    
				currentDelivery = deliveryRepo.getDeiveryByIdAndShipperId(deliId, currrentShipper.getId());
				currentInvoice = currentDelivery.getInvoice();
				List<InvoicesWithProducts> invoiceProductsList = currentInvoice.getInvoicesWithProducts();
				deliveryReportInfo.setDelivery(currentDelivery);
				deliveryReportInfo.setInvoice(currentInvoice);
				deliveryReportInfo.setInvoiceProductsList(invoiceProductsList);
				
				model.addAttribute("deliveryReportInfo", deliveryReportInfo);
			    model.addAttribute("curentcusImage",currrentShipper.convertByteImamgeToBase64String());
			    model.addAttribute("curentcusName",currrentShipper.getName());
			    return "deliver-order-details";
			}
			else {
				GlobalStaticValues.currentPage = "/delivery-report-info-id=" + currentDelivery.getId();
				return "redirect:/loginpage";
			}
	 }
	 
	 
	@GetMapping("/delivery-home")
    public String deliveryHome(HttpSession session, Model model) {
		packingCodInvoicesList = invoiceRepo.getPackingCodInvoices();

		return renderToShipperForms(session, model, packingCodInvoicesList, "/delivery-home");
    }
	
	
	@PostMapping("/delivery-home")
	public String chooseOrderToShip(HttpSession session, Model model, HttpServletRequest request, @RequestParam(value = "action") String action) {
		Invoice invoice = invoiceRepo.getInvoiceById(Integer.parseInt(action));
		
		deliveryRepo.save(new Delivery("shipping", currrentShipper, invoice));
		invoice.setDeliveryStatus("shipping");
		invoiceRepo.save(invoice);
		
		return renderToShipperForms(session, model, packingCodInvoicesList, "/delivery-home");
	}
	
	
	@GetMapping("/delivery-process")
    public String deliveryProcess(HttpSession session, Model model) {
		shipperInvoicesList = invoiceRepo.getShipperInvoicesList(GlobalStaticValues.currentStaff.getId());
		
		return renderToShipperForms(session, model, shipperInvoicesList, "/delivery-process");
    }
	
	
	@GetMapping("/delivery-report-info-id={deliveryId}")
    public String deliveryDetail(HttpSession session, Model model, HttpServletRequest request, @PathVariable("deliveryId") int deliveryId) {
		return renderToDeliveryReportForm(session, model, deliveryId);
    }
	
	
	@PostMapping("/delivery-report-info-id={deliveryId}")
	public String confirmDeliveryStatus(HttpSession session, Model model, HttpServletRequest request, @PathVariable("deliveryId") int deliveryId, @ModelAttribute("deliveryReportInfo") DeliveryReportInfo modelDeliRpInfo) {
		String shipperComment = modelDeliRpInfo.getDelivery().getAdditionalShipperComment();
		String deliveryStatus;
		String invoiceDeliStatus;
		
		if(shipperComment.equals("successful delivery")){
			deliveryStatus = "success";
			invoiceDeliStatus = "shipped";
		} else {
			deliveryStatus = "failed";
			invoiceDeliStatus = "cancel";
		}
		
		currentInvoice.setReason(shipperComment);
		currentInvoice.setDeliveryStatus(invoiceDeliStatus);
		
		currentDelivery.setCurrentStatus(deliveryStatus);
		currentDelivery.setDeliveryDate(new Date());
		currentDelivery.setAdditionalShipperComment(shipperComment);
		
		deliveryRepo.save(currentDelivery);
		invoiceRepo.save(currentInvoice);
		
		return renderToDeliveryReportForm(session, model, deliveryId);
	}
}
