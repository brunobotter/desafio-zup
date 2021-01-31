package br.com.bruno.banco.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.banco.dto.AtualizarContaDto;
import br.com.bruno.banco.dto.ExibeContaDto;
import br.com.bruno.banco.dto.ExtratoDto;
import br.com.bruno.banco.dto.NovaContaDto;
import br.com.bruno.banco.dto.SaqueDepositoDto;
import br.com.bruno.banco.dto.TransferenciaDto;
import br.com.bruno.banco.model.Conta;
import br.com.bruno.banco.service.ContaService;
import br.com.bruno.banco.service.util.ContaExtrato;

@RestController
@RequestMapping("/conta")
public class ContaController {

	@Autowired
	private ContaService contaService;


	/*
	 * Status 200 OK sucesso
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ExibeContaDto>> listar() {
		List<Conta> contas = contaService.listarTodos();
		List<ExibeContaDto> contasDTO = contas.stream().map(conta -> new ExibeContaDto(conta)).collect(Collectors.toList());

		return ResponseEntity.ok(contasDTO);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 404 Nao encontrado
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<ExibeContaDto> buscar(@PathVariable Long id) {
		Conta contaBuscada = contaService.buscar(id);
		ExibeContaDto contaDTO = new ExibeContaDto(contaBuscada);

		if (contaBuscada == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(contaDTO);
	}

	/*
	 * Status 201 Created sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 422 Erro de validação*/
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ExibeContaDto> adicionar(@Valid @RequestBody NovaContaDto contaDto) {
		Conta contaCadastrado = contaService.converteDTOEmEntidade(contaDto);
		contaCadastrado = contaService.cadastrar(contaCadastrado);

		return new ResponseEntity<ExibeContaDto>(new ExibeContaDto(contaCadastrado), HttpStatus.CREATED);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 404 Nao encontrado
	 * Status 422 Erro de validação*/
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<AtualizarContaDto> atualizar(@PathVariable Long id, @Valid @RequestBody AtualizarContaDto contaDto) {
		Conta contaBuscada = contaService.converteDTOEmEntidade(contaDto);
		contaBuscada.setContaId(id);
		contaBuscada = contaService.atualizar(contaBuscada);

		return new ResponseEntity<AtualizarContaDto>(new AtualizarContaDto(contaBuscada), HttpStatus.OK);

	}

	/*
	 * Status 204 NoContent sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 404 Nao encontrado
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		contaService.remover(id);
		return ResponseEntity.noContent().build();
	}

	/*
	 * Status 200 OK sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 422 Erro negocio
	 */
	@PreAuthorize("hasAnyRole('CLIENTE')")
	@PostMapping("/sacar")
	public ResponseEntity<SaqueDepositoDto> sacar(@Valid @RequestBody SaqueDepositoDto saqueDto) {
		ContaExtrato extrato = contaService.sacar(saqueDto);

		return new ResponseEntity<SaqueDepositoDto>(new SaqueDepositoDto(extrato), HttpStatus.OK);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 422 Erro negocio
	 */
	@PostMapping("/depositar")
	public ResponseEntity<SaqueDepositoDto> depositar(@Valid @RequestBody SaqueDepositoDto depositoDTO) {
		ContaExtrato extrato = contaService.depositar(depositoDTO);

		return new ResponseEntity<SaqueDepositoDto>(new SaqueDepositoDto(extrato), HttpStatus.OK);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 422 Erro negocio
	 */
	@PreAuthorize("hasAnyRole('CLIENTE')")
	@PostMapping("/transferir")
	public ResponseEntity<ExtratoDto> transferir(@Valid @RequestBody TransferenciaDto transferenciaDTO) {
		ContaExtrato contaExtrato = contaService.transferir(transferenciaDTO);

		return new ResponseEntity<ExtratoDto>(new ExtratoDto(contaExtrato), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/page")
	public ResponseEntity<Page<ExibeContaDto>> buscaPaginada(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "numeroConta") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<Conta> list = contaService.buscaPaginada(page, linesPerPage, orderBy, direction);
		Page<ExibeContaDto> listDto = list.map(obj -> new ExibeContaDto(obj));

		return ResponseEntity.ok(listDto);
	}
}
