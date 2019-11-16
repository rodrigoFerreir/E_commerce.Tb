package com.ferreira.rodrigo.project.ecommerce.tb.servicos;

import org.springframework.security.core.context.SecurityContextHolder;

import com.ferreira.rodrigo.project.ecommerce.tb.security.UserSecurity;

public class UserService {
	
	public static UserSecurity autheticaded() {
		try {
		return (UserSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //retorna o usuario logado no sistema
		}
		catch(Exception e) {
			return null;
		}
	}
}
