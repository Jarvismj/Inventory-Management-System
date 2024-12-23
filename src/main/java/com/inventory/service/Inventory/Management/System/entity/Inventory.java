package com.inventory.service.Inventory.Management.System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Inventory_Data")
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
		return "Inventory [productCode=" + productCode + ", quantity=" + quantity + ", productName=" + productName
				+ ", productPrice=" + productPrice + "]";
	}

}
