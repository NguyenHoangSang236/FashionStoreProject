package com.example.demo.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	@Query(value = "select * from invoice where customer_id = :idVal", nativeQuery = true)
	List<Invoice> getPaymentHistoryByCustomerId(@Param("idVal") int id);
	
	
	@Query(value = "select id from invoice order by id desc limit 1", nativeQuery = true)
	int getLastestInvoiceId();
	
	
	@Query(value = "select * from invoice", nativeQuery = true)
	List<Invoice> getAllInvoices();
	
	
	@Query(value = "select * from invoice where id = :idVal", nativeQuery = true)
	Invoice getInvoiceById(@Param("idVal") int id);
	
	
	@Query(value = "select * from invoice where Payment_Method != 'cod'", nativeQuery = true)
	List<Invoice> getOnlinePaymentInvoices();
	
	
	@Query(value = "select * from invoice where Payment_Method = 'cod' and admin_acceptance = 'waiting'", nativeQuery = true)
	List<Invoice> getWaitingCodInvoices();
}
