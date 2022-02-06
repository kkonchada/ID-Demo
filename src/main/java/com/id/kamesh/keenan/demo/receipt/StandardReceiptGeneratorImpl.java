package com.id.kamesh.keenan.demo.receipt;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.id.kamesh.keenan.demo.prodinfo.ProductInfo;

@Service
public class StandardReceiptGeneratorImpl implements ReceiptGenerator {
	private static final String RECEIPT_DATE_FORMAT = "MMMM dd, yyyy hh:mm a";
	
	@Value("${app.stateTaxPercentage}")
	private float stateTaxPercentage;
	
	@Value("${app.countyTaxPercentage}")
	private float countyTaxPercentage;
	
	@Value("${app.cityTaxPercentage}")
	private float cityTaxPercentage;
	
	public String generateReceipt(float amountPaid, List<ProductInfo> purchasedItems){
		
		StringBuffer receiptBody = new StringBuffer();
		float subTotal = 0;
		float nonGroceryTotal = 0;
		
		//add header
		receiptBody.append("*****************************************************\n");
		receiptBody.append("\tKeenan Convenience Store");
		receiptBody.append("\n");
		receiptBody.append("\t"+LocalDateTime.now().format(DateTimeFormatter.ofPattern(RECEIPT_DATE_FORMAT)));
		receiptBody.append("\n\n");
		
		//add items and collect totals
		for(ProductInfo item:purchasedItems) {
			receiptBody.append(item.getName());
			receiptBody.append("\t"+item.getIdentifier());
			receiptBody.append("\t"+NumberFormat.getCurrencyInstance().format(item.getPrice()));
			receiptBody.append("\t("+item.getCategory()+")");	
			receiptBody.append("\n");
			
			subTotal += item.getPrice();
			if(!item.getCategory().equals("g"))
				nonGroceryTotal += item.getPrice();
		}
		receiptBody.append("\n");
		
		//add Totals
		receiptBody.append("\tSubTotal: "+NumberFormat.getCurrencyInstance().format(subTotal));	
		receiptBody.append("\n");
		
		float stateTax = nonGroceryTotal*stateTaxPercentage/100;
		receiptBody.append("\tState Tax ("+stateTaxPercentage+"%): "+NumberFormat.getCurrencyInstance().format(stateTax));	
		receiptBody.append("\n");
		
		float countyTax = nonGroceryTotal*countyTaxPercentage/100;
		receiptBody.append("\tCounty Tax ("+countyTaxPercentage+"%): "+NumberFormat.getCurrencyInstance().format(countyTax));	
		receiptBody.append("\n");
		
		float cityTax = subTotal*cityTaxPercentage/100;
		receiptBody.append("\tCity Tax ("+cityTaxPercentage+"%): "+NumberFormat.getCurrencyInstance().format(cityTax));	
		receiptBody.append("\n");
		
		float amountOwed = subTotal + stateTax + countyTax + cityTax;
		
		receiptBody.append("\tTotal Due: "+NumberFormat.getCurrencyInstance().format(amountOwed));
		receiptBody.append("\n");
		
		receiptBody.append("\tAmount Paid: "+NumberFormat.getCurrencyInstance().format(amountPaid));
		receiptBody.append("\n");
		
		
		float changeDue = amountPaid - amountOwed;
		receiptBody.append("\tChange Due: "+NumberFormat.getCurrencyInstance().format(changeDue));
		receiptBody.append("\n");
		
		receiptBody.append("*****************************************************\n");
		
		return receiptBody.toString();
		
	}
	
	

}
