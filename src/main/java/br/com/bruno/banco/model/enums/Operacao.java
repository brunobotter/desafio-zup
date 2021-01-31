package br.com.bruno.banco.model.enums;

public enum Operacao {

	DEPOSITO("Deposito"), SAQUE("Saque"), TRANSFERENCIA("Transferência");

	private String descricao;

	Operacao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
