package com.id.kamesh.keenan.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.id.kamesh.keenan.demo.prodinfo.ProductInfo;
import com.id.kamesh.keenan.demo.prodsearch.ProductFinder;
import com.id.kamesh.keenan.demo.receipt.ReceiptGenerator;
import com.id.kamesh.keenan.demo.util.LogUtil;

@SpringBootApplication
public class KeenanReceiptGeneratorApplication {

	private static final Logger Log = LoggerFactory.getLogger(KeenanReceiptGeneratorApplication.class);

	private static ProductFinder productFinderService;

	public static void main(String[] args) {
		ConfigurableApplicationContext appContext = SpringApplication.run(KeenanReceiptGeneratorApplication.class,
				args);

		// get service beans
		productFinderService = appContext.getBean(ProductFinder.class);
		ReceiptGenerator receiptGeneratorService = appContext.getBean(ReceiptGenerator.class);
		LogUtil logUtil = appContext.getBean(LogUtil.class);

		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.print("Enter Amount Paid and Purchased Product List (X to exit): ");
				String input = scanner.nextLine().trim();
				if (input.equalsIgnoreCase("x"))
					break;
				Log.info("Input Received: " + input);
				try {
					float amountPaid = Float.parseFloat(input.substring(0, input.indexOf(' ')));
					List<ProductInfo> purchasedItems = parseInput(input.substring(input.indexOf(' ') + 1));
					String receiptBody = receiptGeneratorService.generateReceipt(amountPaid, purchasedItems);
					System.out.print(receiptBody);
				} catch (NumberFormatException | StringIndexOutOfBoundsException | NoSuchElementException nfex) {
					Log.error(logUtil.stackTraceToString(nfex));
					System.out.println("Error: Input invalid");
				}
			}
		}
	}

	private static List<ProductInfo> parseInput(String purchasedItemString) {

		String[] items = purchasedItemString.split(" ");

		ArrayList<ProductInfo> itemList = new ArrayList<ProductInfo>();
		for (String itemID : items) {
			itemList.add(productFinderService.findProductbyID(itemID));
		}
		return itemList;
	}

}
