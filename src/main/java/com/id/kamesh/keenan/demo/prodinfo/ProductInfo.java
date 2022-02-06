package com.id.kamesh.keenan.demo.prodinfo;

public class ProductInfo {
	
	private String identifier;
	private String name;
	private float price;
	private String category;
	
		
	public ProductInfo() {
		super();
	}

	public ProductInfo(String identifier, String name, float price, String category) {
		super();
		this.identifier = identifier;
		this.name = name;
		this.price = price;
		this.category = category;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
}
