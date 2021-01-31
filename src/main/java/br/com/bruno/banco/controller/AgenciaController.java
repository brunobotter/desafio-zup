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

import br.com.bruno.banco.dto.AgenciaDto;
import br.com.bruno.banco.dto.ListaAgenciaDto;
import br.com.bruno.banco.dto.NovaAgenciaDto;
import br.com.bruno.banco.model.Agencia;
import br.com.bruno.banco.service.AgenciaService;

@RestController
@RequestMapping("/agencia")
public class AgenciaController {

	@Autowired
	private AgenciaService agenciaService;

	/*
	 * Status 200 OK sucesso
	 */
	@GetMapping
	public ResponseEntity<List<ListaAgenciaDto>> listar() {
		List<Agencia> agencias = agenciaService.listarTodos();
		List<ListaAgenciaDto> agenciasDTO = agencias.stream().map(agencia -> new ListaAgenciaDto(agencia))
				.collect(Collectors.toList());

		return ResponseEntity.ok(agenciasDTO);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 404 Nao encontrado
	 */
	@GetMapping("/{id}")
	public ResponseEntity<AgenciaDto> buscar(@PathVariable Long id) {
		Agencia agenciaBuscada = agenciaService.buscar(id);

		if (agenciaBuscada == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new AgenciaDto(agenciaBuscada));
	}

	/*
	 * Status 201 Created sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 422 Erro de validação*/
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<NovaAgenciaDto> adicionar(@Valid @RequestBody NovaAgenciaDto agenciaDto) {
		Agencia agenciaCadastrado = agenciaService.converteDTOEmEntidade(agenciaDto);
		agenciaCadastrado = agenciaService.salvar(agenciaCadastrado);

		return new ResponseEntity<NovaAgenciaDto>(new NovaAgenciaDto(agenciaCadastrado), HttpStatus.CREATED);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 404 Nao encontrado
	 * Status 403 Forbiden acesso negado
	 * Status 422 Erro de validação*/
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<AgenciaDto> atualizar(@PathVariable Long id, @Valid @RequestBody AgenciaDto agenciaDto) {
		Agencia agenciaBuscada = agenciaService.converteDTOEmEntidade(agenciaDto);
		agenciaBuscada.setAgenciaId(id);
		agenciaBuscada = agenciaService.atualizar(agenciaBuscada);

		return new ResponseEntity<AgenciaDto>(new AgenciaDto(agenciaBuscada), HttpStatus.OK);
	}

	/*
	 * Status 204 NoContent sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 404 Nao encontrado
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		agenciaService.remover(id);
		return ResponseEntity.noContent().build();
	}

	/*
	 * Status 200 OK sucesso
	 */
	@GetMapping("/page")
	public ResponseEntity<Page<ListaAgenciaDto>> buscaPaginada(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "numeroAgencia") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<Agencia> list = agenciaService.buscaPaginada(page, linesPerPage, orderBy, direction);
		Page<ListaAgenciaDto> listDto = list.map(obj -> new ListaAgenciaDto(obj));

		return ResponseEntity.ok().body(listDto);
	}
}
