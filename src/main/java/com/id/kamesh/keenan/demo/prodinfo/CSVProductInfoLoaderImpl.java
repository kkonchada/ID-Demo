package com.id.kamesh.keenan.demo.prodinfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CSVProductInfoLoaderImpl implements ProductInfoLoader{
	
	private static final Logger Log = LoggerFactory.getLogger(CSVProductInfoLoaderImpl.class);
	
	private Map<String, ProductInfo> prodInfoMap = new HashMap<String, ProductInfo>();
	
	@Value("${app.prodInfo.file}")
	String prodInfoFile;
	
	public CSVProductInfoLoaderImpl(){}
	
	@PostConstruct
	public void init() throws IOException{
		
		Path path = Paths.get(prodInfoFile);
		Files.lines(path).forEach(line ->{
			String[] prodInfoFromLine = line.split(",");
			ProductInfo prodInfo = new  ProductInfo(prodInfoFromLine[0], prodInfoFromLine[1], Float.parseFloat(prodInfoFromLine[2]), prodInfoFromLine[3]);
			prodInfoMap.put(prodInfoFromLine[0], prodInfo);
		});
		Log.info("Loaded "+prodInfoMap.size()+" products from product info file");
	}
	
	public Map<String, ProductInfo> getProductInfo(){
		return prodInfoMap;
	}
	
	
	

}
