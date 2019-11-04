package com.ferreira.rodrigo.project.ecommerce.tb.servicos;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ferreira.rodrigo.project.ecommerce.tb.model.PagamentoComBoleto;

@Service
public class BoletoService {
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instantePedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instantePedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}//Intanciando um obj do tipo Calendar para definir datas de vencimento do boleto no arquivo txt.
