package com.id.kamesh.keenan.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.id.kamesh.keenan.demo.prodinfo.ProductInfo;
import com.id.kamesh.keenan.demo.prodinfo.ProductInfoLoader;
import com.id.kamesh.keenan.demo.prodsearch.ProductFinder;
import com.id.kamesh.keenan.demo.receipt.ReceiptGenerator;

@SpringBootTest
public class AppTests {
	
	@MockBean
	private ProductInfoLoader prodInfoLoader;

	@Autowired
	private ReceiptGenerator receiptGenerator;

	@Autowired
	private ProductFinder productFinder;

	private List<ProductInfo> prodListPurchased = new ArrayList<ProductInfo>();

	@BeforeEach
	public void init() {
		ProductInfo pf1 = new ProductInfo("11111", "prod1", 1.99f, "g");
		ProductInfo pf9 = new ProductInfo("99999", "prod9", 9.99f, "o");
		Map<String, ProductInfo> productMap = new HashMap<String, ProductInfo>();
		productMap.put("11111", pf1);
		productMap.put("99999", pf9);

		prodListPurchased.add(pf1);
		prodListPurchased.add(pf9);

		when(prodInfoLoader.getProductInfo()).thenReturn(productMap);
	}

	
	@Test
	public void testproductFinder() {

		ProductInfo prodInfo1 = productFinder.findProductbyID("11111");
		assertEquals("prod1", prodInfo1.getName());

		ProductInfo prodInfo9 = productFinder.findProductbyID("99");
		assertEquals("prod9", prodInfo9.getName());
	}
	
	@Test
	public void testReceiptBuilder() {

		String receiptText = receiptGenerator.generateReceipt(20f, prodListPurchased);
		assertTrue(receiptText.contains("11.98"));
	}

}
