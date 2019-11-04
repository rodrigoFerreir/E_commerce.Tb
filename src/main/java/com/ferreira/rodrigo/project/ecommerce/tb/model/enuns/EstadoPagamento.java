package com.ferreira.rodrigo.project.ecommerce.tb.model.enuns;

public enum EstadoPagamento {
		PENDENTE(1, "Pendente"),
		QUITADO(2, "Quitado"),
		CANCELADO(3, "Cancelado");
		
		private int cod;
		private String desc;
		
		private EstadoPagamento(int cod, String desc) {
			this.cod = cod;
			this.desc = desc;
		}

		public int getCod() {
			return cod;
		}

		public String getDesc() {
			return desc;
		}
		
		//Metodo para percorrer a lista procurando um codigo e verificando se esse codigo é valido.
		public static EstadoPagamento toEnum(Integer cod) {
			if(cod == null) {
				return null;
			}
			
			for(EstadoPagamento x: EstadoPagamento.values()) {
				if(cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id inválido: " + cod);
		}

}
