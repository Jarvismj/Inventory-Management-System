package com.inventory.service.Inventory.Management.System.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Inventory_Data")
//@SequenceGenerator(name = "seqid-gen", sequenceName = "Product_Code_Sequence",initialValue = 45000, allocationSize = 1)
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// ,generator="seqid-gen")
	@Column(name = "productCode")
	private Long productCode;
	@Column(name = "quantity")
	private Long quantity;
	@Column(name = "productName")
	private String productName;
	@Column(name = "productPrice")
	private String productPrice;

	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Inventory(Long productCode, Long quantity, String productName, String productPrice) {
		super();
		this.productCode = productCode;
		this.quantity = quantity;
		this.productName = productName;
		this.productPrice = productPrice;
	}

	public Long getProductCode() {
		return productCode;
	}

	public void setProductCode(Long productCode) {
		this.productCode = productCode;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public String toString() {
		return "Inventory [orderId=" + productCode + ", quantity=" + quantity + ", productName=" + productName
				+ ", productPrice=" + productPrice + "]";
	}

}
