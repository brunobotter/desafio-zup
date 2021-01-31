package br.com.bruno.banco.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.bruno.banco.model.Conta;

public class NovaContaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Preenchimento obrigatório")
	private Long bancoId;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String agenciaNumero;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Pattern(regexp = "\\d{5}-\\d{1}", message = "O número da conta deve possuir o seguinte formato 00000-0 e ser formado apenas por números!")
	private String numero;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String tipo;

	@PositiveOrZero(message = "O saldo deve ser maior ou igual a zero")
	@NotNull(message = "Preenchimento obrigatório")
	private Double saldo;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatório")
	@CPF(message = "O CPF informado é inválido")
	private String cpf;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String senha;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "O email informado é inválido")
	private String email;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataNascimento;
	
	public NovaContaDto() {
		
	}

	public NovaContaDto(Conta conta) {
		super();
		this.bancoId = conta.getAgencia().getBanco().getBancoId();
		this.agenciaNumero = conta.getAgencia().getNumeroAgencia();
		this.numero = conta.getNumeroConta();
		this.tipo = conta.getTipo().getDescricao();
		this.saldo = conta.getSaldo();
		this.nome = conta.getCliente().getNome();
		this.cpf = conta.getCliente().getCpf();
		this.senha = conta.getCliente().getSenha();
		this.email = conta.getCliente().getEmail();
		this.dataNascimento = conta.getCliente().getDataNascimento();
	}

	public Long getBancoId() {
		return bancoId;
	}

	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
	}

	public String getAgenciaNumero() {
		return agenciaNumero;
	}

	public void setAgenciaNumero(String agenciaNumero) {
		this.agenciaNumero = agenciaNumero;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	

}
