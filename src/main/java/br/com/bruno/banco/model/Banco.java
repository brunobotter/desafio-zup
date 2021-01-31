package br.com.bruno.banco.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Banco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "banco_id")
	private Long bancoId;

	@Column(name = "nome")
	private String nome;

	public Banco() {
		super();
	}

	public Banco(Long bancoId) {
		super();
		this.bancoId = bancoId;
	}

	public Banco(String nome) {
		super();
		this.nome = nome;
	}

	public Banco(Long bancoId, String nome) {
		super();
		this.bancoId = bancoId;
		this.nome = nome;
	}

	public Long getBancoId() {
		return bancoId;
	}

	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bancoId == null) ? 0 : bancoId.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Banco other = (Banco) obj;
		if (bancoId == null) {
			if (other.bancoId != null)
				return false;
		} else if (!bancoId.equals(other.bancoId))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
