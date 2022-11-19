package com.example.demo.entity.embededkey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InvoicesWithProductsPrimaryKeys implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "product_id")
    int productId;

    @Column(name = "invoice_id")
    int invoiceId;

}
