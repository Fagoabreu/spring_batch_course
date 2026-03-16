package com.springbatch.contasbancarias.dominio;

public enum TipoConta {
	PRATA, OURO, PLATINA, DIAMANTE, INVALIDA;
	
	public static TipoConta fromFaixaSalarial(Double faixaSalarial) {
		if (faixaSalarial ==null)
			return INVALIDA;
		if (faixaSalarial <= 3000)
			return PRATA;
		if (faixaSalarial <= 5000)
			return OURO;
		if (faixaSalarial <= 10000)
			return PLATINA;

		return DIAMANTE;
	}
}
