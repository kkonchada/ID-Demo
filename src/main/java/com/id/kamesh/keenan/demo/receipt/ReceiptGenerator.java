package com.id.kamesh.keenan.demo.receipt;

import java.util.List;

import com.id.kamesh.keenan.demo.prodinfo.ProductInfo;

public interface ReceiptGenerator {
	
	public String generateReceipt(float amountPaid, List<ProductInfo> purchasedItems);
	

}
