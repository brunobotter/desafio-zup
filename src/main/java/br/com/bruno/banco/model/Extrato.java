package br.com.bruno.banco.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.bruno.banco.model.enums.Operacao;

@Entity
public class Extrato implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "extrato_id")
	private Long extratoId;

	@Column(name = "data")
	private LocalDateTime data;

	@Enumerated(EnumType.STRING)
	@Column(name = "operacao")
	private Operacao operacao;

	@Column(name = "valor")
	private Double valor;

	@Column(name = "informacao")
	private String informacoes;

	@ManyToOne
	private Conta conta;

	public Extrato(LocalDateTime data, Operacao operacao, Double valor, Conta conta) {
		super();
		this.data = data;
		this.operacao = operacao;
		this.valor = valor;
		this.conta = conta;
	}

	public Long getExtratoId() {
		return extratoId;
	}

	public void setExtratoId(Long extratoId) {
		this.extratoId = extratoId;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getInformacoes() {
		return informacoes;
	}

	public void setInformacoes(String informacoes) {
		this.informacoes = informacoes;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extratoId == null) ? 0 : extratoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Extrato other = (Extrato) obj;
		if (extratoId == null) {
			if (other.extratoId != null)
				return false;
		} else if (!extratoId.equals(other.extratoId))
			return false;
		return true;
	}

}
