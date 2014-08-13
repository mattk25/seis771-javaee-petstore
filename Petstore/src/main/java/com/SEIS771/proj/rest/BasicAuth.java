package com.SEIS771.proj.rest;

import javax.xml.bind.DatatypeConverter;

public class BasicAuth {
	public static String[] decode(String auth) {
		auth = auth.replaceFirst("[B|b]asic ", "");
		byte[] decodedBytes = DatatypeConverter.parseBase64Binary(auth);
		
		if(decodedBytes == null || decodedBytes.length == 0) {
			return null;
		}
		
		return new String(decodedBytes).split(":", 2);
	}
	
	public static void main(String args[]) {
		System.out.println(decode("bWtlc2luZ2VyOm1hdHQ1NDYx")[0]);
		System.out.println(decode("bWtlc2luZ2VyOm1hdHQ1NDYx")[1]);
	}
}


