package com.ferreira.rodrigo.project.ecommerce.tb.model.enuns;

public enum Perfil {
		ADMIN(1, "ROLE_ADMIN"),
		CLIENTE(2, "ROLE_CLIENTE");
		
		private int cod;
		private String desc;
		
		private Perfil(int cod, String desc) {
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
		public static Perfil toEnum(Integer cod) {
			if(cod == null) {
				return null;
			}
			
			for(Perfil x: Perfil.values()) {
				if(cod.equals(x.getCod())) {
					return x;
				}
			}
			
			throw new IllegalArgumentException("Id inválido: " + cod);
		}

}
