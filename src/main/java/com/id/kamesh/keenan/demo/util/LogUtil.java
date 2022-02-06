package com.id.kamesh.keenan.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.stereotype.Component;

@Component
public class LogUtil {
	
	public String stackTraceToString(Exception ex){
		StringWriter stringWriter= new StringWriter();
		PrintWriter printWriter= new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		return stringWriter.toString();
	}

}
