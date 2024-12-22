package com.inventory.service.Inventory.Management.System.dto;

public class InventoryRequest {

	private Long productCode;
	private Long quantity;
	private String productName;
	private String productPrice;
	private Long newQuantity;

	public InventoryRequest(Long productCode, Long quantity, String productName, String productPrice,
			Long newQuantity) {
		super();
		this.productCode = productCode;
		this.quantity = quantity;
		this.productName = productName;
		this.productPrice = productPrice;
		this.newQuantity = newQuantity;
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

	public Long getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(Long newQuantity) {
		this.newQuantity = newQuantity;
	}

	@Override
	public String toString() {
		return "InventoryRequest [productCode=" + productCode + ", quantity=" + quantity + ", productName="
				+ productName + ", productPrice=" + productPrice + ", newQuantity=" + newQuantity + "]";
	}

}
