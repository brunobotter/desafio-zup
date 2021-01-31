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
import br.com.bruno.banco.dto.BancoDto;
import br.com.bruno.banco.model.Agencia;
import br.com.bruno.banco.model.Banco;
import br.com.bruno.banco.service.AgenciaService;
import br.com.bruno.banco.service.BancoService;

@RestController
@RequestMapping("/banco")
public class BancoController {

	@Autowired
	private BancoService bancoService;

	@Autowired
	private AgenciaService agenciaService;

	/*
	 * Status 200 OK sucesso
	 */
	@GetMapping
	public ResponseEntity<List<BancoDto>> listar() {
		List<Banco> bancos = bancoService.listarTodos();
		List<BancoDto> bancosDTO = bancos.stream().map(banco -> new BancoDto(banco)).collect(Collectors.toList());

		return ResponseEntity.ok(bancosDTO);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 404 Nao encontrado
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Banco> buscar(@PathVariable Long id) {
		Banco bancoBuscado = bancoService.buscar(id);

		if (bancoBuscado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(bancoBuscado);
	}

	/*
	 * Status 201 Created sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 422 Erro de validação*/
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<BancoDto> adicionar(@Valid @RequestBody BancoDto bancoDto) {
		Banco bancoCadastrado = bancoService.converteDTOEmEntidade(bancoDto);
		bancoCadastrado = bancoService.salvar(bancoCadastrado);

		return new ResponseEntity<BancoDto>(new BancoDto(bancoCadastrado), HttpStatus.CREATED);
	}

	
	/*
	 * Status 200 OK sucesso
	 * Status 404 Nao encontrado
	 * Status 403 Forbiden acesso negado
	 * Status 422 Erro de validação*/
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<BancoDto> atualizar(@PathVariable Long id, @Valid @RequestBody BancoDto bancoDto) {
		Banco bancoBuscada = bancoService.converteDTOEmEntidade(bancoDto);
		bancoBuscada.setBancoId(id);
		bancoBuscada = bancoService.atualizar(bancoBuscada);

		return new ResponseEntity<BancoDto>(new BancoDto(bancoBuscada), HttpStatus.OK);
	}

	/*
	 * Status 204 NoContent sucesso
	 * Status 404 Nao encontrado
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		bancoService.remover(id);
		return ResponseEntity.noContent().build();
	}

	/*
	 * Status 200 OK sucesso
	 */
	@GetMapping("/page")
	public ResponseEntity<Page<BancoDto>> buscaPaginada(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<Banco> list = bancoService.buscaPaginada(page, linesPerPage, orderBy, direction);
		Page<BancoDto> listDto = list.map(obj -> new BancoDto(obj));

		return ResponseEntity.ok().body(listDto);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 404 Nao encontrado
	 */
	@GetMapping("/{id}/agencia")
	public ResponseEntity<List<AgenciaDto>> buscarAgencias(@PathVariable Long id) {
		List<Agencia> agencias = agenciaService.buscarPorBanco(id);
		List<AgenciaDto> agenciasDTO = agencias.stream().map(agencia -> new AgenciaDto(agencia))
				.collect(Collectors.toList());
		if (agencias.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(agenciasDTO);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 404 Nao encontrado
	 */
	@GetMapping("/{id}/agencia/{numero}")
	public ResponseEntity<AgenciaDto> buscarAgencia(@PathVariable Long id, @PathVariable String numero) {
		Agencia agenciaBuscada = agenciaService.buscarPorNumero(id, numero);

		if (agenciaBuscada == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new AgenciaDto(agenciaBuscada));
	}

}
