package br.com.bruno.banco.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.bruno.banco.dto.NovoClienteDto;
import br.com.bruno.banco.model.enums.Role;

@Entity
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id")
	private Long clienteId;

	@Column(name = "usuario", unique = true)
	private String usuario;

	@Column(name = "nome")
	private String nome;

	@JsonIgnore
	@Column(name = "senha")
	private String senha;

	@Column(name = "email")
	private String email;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();

	public void addPerfil(Role perfil) {
		this.perfis.add(perfil.getCodigo());
	}

	public Cliente() {
		super();
	}

	public Cliente(Long clienteId, String nome, String cpf, String senha) {
		super();
		this.clienteId = clienteId;
		this.nome = nome;
		this.cpf = cpf;
		this.senha = senha;
	}

	
	
	public Cliente(String nome, String cpf, String senha, String email, LocalDate dataNascimento) {
		super();
		this.nome = nome;
		this.senha = senha;
		this.cpf = cpf;
		this.email = email;
		this.dataNascimento = dataNascimento;
		addPerfil(Role.CLIENTE);
	}

	public Cliente(Long clienteId, String usuario, String nome, String senha, String email, String cpf,
			LocalDate dataNascimento) {
		super();
		this.clienteId = clienteId;
		this.usuario = usuario;
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		addPerfil(Role.CLIENTE);
	}

	public Cliente(String usuario, String nome, String senha, String email, String cpf, LocalDate dataNascimento) {
		super();
		this.usuario = usuario;
		this.nome = nome;
		this.senha = senha;
		this.email = email;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		addPerfil(Role.CLIENTE);
	}
	
	public Cliente(NovoClienteDto novoClienteDto) {
		super();
		this.nome = novoClienteDto.getNome();
		this.cpf = novoClienteDto.getCpf();
		this.senha = novoClienteDto.getSenha();
		this.email = novoClienteDto.getEmail();
		this.dataNascimento = novoClienteDto.getDataNascimento();
		addPerfil(Role.CLIENTE);
	}
	

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Set<Role> getPerfis() {
		return this.perfis.stream().map(p -> Role.toEnum(p)).collect(Collectors.toSet());
	}

	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clienteId == null) ? 0 : clienteId.hashCode());
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
		Cliente other = (Cliente) obj;
		if (clienteId == null) {
			if (other.clienteId != null)
				return false;
		} else if (!clienteId.equals(other.clienteId))
			return false;
		return true;
	}

}
