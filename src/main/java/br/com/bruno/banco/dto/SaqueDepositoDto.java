package br.com.bruno.banco.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import br.com.bruno.banco.service.util.ContaExtrato;

public class SaqueDepositoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Preenchimento obrigatório")
	private Long bancoId;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String agenciaNumero;

	@NotEmpty(message = "Preenchimento obrigatório")
	@Pattern(regexp = "\\d{5}-\\d{1}", message = "O número da conta deve possuir o seguinte formato 00000-0 e ser formado apenas por números!")
	private String contaNumero;

	@NotEmpty(message = "Preenchimento obrigatório")
	private String tipo;

	@PositiveOrZero(message = "O valor deve ser maior ou igual a zero")
	@NotNull(message = "Preenchimento obrigatório")
	private Double valor;
	
	public String informacoes;

	public SaqueDepositoDto() {

	}

	public SaqueDepositoDto(ContaExtrato conta) {
		this.bancoId = conta.getConta().getAgencia().getBanco().getBancoId();
		this.agenciaNumero = conta.getConta().getAgencia().getNumeroAgencia();
		this.contaNumero = conta.getConta().getNumeroConta();
		this.tipo = conta.getExtrato().getOperacao().getDescricao();
		this.valor = conta.getExtrato().getValor();
		this.informacoes = conta.getExtrato().getInformacoes();
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

	public String getContaNumero() {
		return contaNumero;
	}

	public void setContaNumero(String contaNumero) {
		this.contaNumero = contaNumero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
