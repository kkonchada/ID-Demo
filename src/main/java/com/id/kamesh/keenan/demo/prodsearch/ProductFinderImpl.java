package com.id.kamesh.keenan.demo.prodsearch;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.id.kamesh.keenan.demo.prodinfo.ProductInfo;
import com.id.kamesh.keenan.demo.prodinfo.ProductInfoLoader;

@Service
public class ProductFinderImpl implements ProductFinder{
	
	private static final Logger Log = LoggerFactory.getLogger(ProductFinderImpl.class);
	
	@Autowired
	ProductInfoLoader loaderService;
	
	public ProductInfo findProductbyID(String identifier){
		Map<String, ProductInfo> prodMap = loaderService.getProductInfo();
		String keyOfInterest = identifier;
		if(keyOfInterest.length() < 12){
			Log.debug("Looking up item number:"+keyOfInterest);
			keyOfInterest = prodMap.keySet().parallelStream().filter(prodID -> prodID.startsWith(identifier)).findFirst().get();
		}
		return prodMap.get(keyOfInterest);
	}

}
