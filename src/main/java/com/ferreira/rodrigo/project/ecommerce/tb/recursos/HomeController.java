package com.ferreira.rodrigo.project.ecommerce.tb.recursos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@GetMapping("/")//chamando a raiz do projeto(home)
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("views/index.xhtml");
		return mv;
	}

}
