package com.ferreira.rodrigo.project.ecommerce.tb.recursos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {
	
	public static String decoderParam(String s) {
		try {
		URLDecoder.decode(s, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			return "";
		}
		return s;
	}
	
	public static List<Integer> decoderIntList(String s){
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for(int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		
		return list;
	}
}//Esses metodos converte os parametos de busca de produtos, facilitando a busca pelo GET do http.
