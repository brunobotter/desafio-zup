package br.com.bruno.banco.model.enums;

public enum TipoConta {
	
	CORRENTE("Conta corrente"), POUPANCA("Conta poupança");

	private String descricao;

	TipoConta(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
