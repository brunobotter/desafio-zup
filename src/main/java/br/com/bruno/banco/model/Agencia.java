package br.com.bruno.banco.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Agencia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agencia_id")
	private Long agenciaId;

	@ManyToOne
	@JoinColumn(name = "banco_id")
	private Banco banco;

	@Column(name = "numero_agencia")
	private String numeroAgencia;

	@Column(name = "nome_agencia")
	private String nomeAgencia;

	public Agencia() {
		super();
	}

	public Agencia(String numeroAgencia, String nomeAgencia) {
		super();
		this.numeroAgencia = numeroAgencia;
		this.nomeAgencia = nomeAgencia;
	}

	public Agencia(Long agenciaId, Banco banco, String numeroAgencia, String nomeAgencia) {
		super();
		this.agenciaId = agenciaId;
		this.banco = banco;
		this.numeroAgencia = numeroAgencia;
		this.nomeAgencia = nomeAgencia;
	}

	public Agencia( String numeroAgencia, String nomeAgencia,Banco banco) {
		super();
		this.banco = banco;
		this.numeroAgencia = numeroAgencia;
		this.nomeAgencia = nomeAgencia;
	}

	public Long getAgenciaId() {
		return agenciaId;
	}

	public void setAgenciaId(Long agenciaId) {
		this.agenciaId = agenciaId;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getNumeroAgencia() {
		return numeroAgencia;
	}

	public void setNumeroAgencia(String numeroAgencia) {
		this.numeroAgencia = numeroAgencia;
	}

	public String getNomeAgencia() {
		return nomeAgencia;
	}

	public void setNomeAgencia(String nomeAgencia) {
		this.nomeAgencia = nomeAgencia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agenciaId == null) ? 0 : agenciaId.hashCode());
		result = prime * result + ((banco == null) ? 0 : banco.hashCode());
		result = prime * result + ((nomeAgencia == null) ? 0 : nomeAgencia.hashCode());
		result = prime * result + ((numeroAgencia == null) ? 0 : numeroAgencia.hashCode());
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
		Agencia other = (Agencia) obj;
		if (agenciaId == null) {
			if (other.agenciaId != null)
				return false;
		} else if (!agenciaId.equals(other.agenciaId))
			return false;
		if (banco == null) {
			if (other.banco != null)
				return false;
		} else if (!banco.equals(other.banco))
			return false;
		if (nomeAgencia == null) {
			if (other.nomeAgencia != null)
				return false;
		} else if (!nomeAgencia.equals(other.nomeAgencia))
			return false;
		if (numeroAgencia == null) {
			if (other.numeroAgencia != null)
				return false;
		} else if (!numeroAgencia.equals(other.numeroAgencia))
			return false;
		return true;
	}

}
