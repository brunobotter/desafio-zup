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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.banco.dto.ClienteDto;
import br.com.bruno.banco.dto.NovoClienteDto;
import br.com.bruno.banco.model.Cliente;
import br.com.bruno.banco.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	/*
	 * Status 200 OK sucesso
	 * Status 403 Forbiden acesso negado
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClienteDto>> listar() {
		List<Cliente> clientes = clienteService.listarTodos();
		List<ClienteDto> clientesDTO = clientes.stream().map(cliente -> new ClienteDto(cliente))
				.collect(Collectors.toList());

		return ResponseEntity.ok(clientesDTO);
	}

	/*
	 * Status 200 OK sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 404 Nao encontrado
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDto> buscar(@PathVariable Long id) {
		Cliente clienteBuscado = clienteService.buscar(id);

		if (clienteBuscado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(new ClienteDto(clienteBuscado));
	}

	
	/*
	 * Status 200 OK sucesso
	 * Status 404 Nao encontrado
	 * Status 403 Forbiden acesso negado
	 * Status 422 Erro de validação*/
	@PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<ClienteDto> atualizar(@PathVariable Long id, @Valid @RequestBody NovoClienteDto clienteDto) {
		Cliente clienteBuscada = clienteService.converteNovoClienteDTOEmEntidade(clienteDto);
		clienteBuscada.setClienteId(id);
		clienteBuscada = clienteService.atualizar(clienteBuscada);

		return new ResponseEntity<ClienteDto>(new ClienteDto(clienteBuscada), HttpStatus.OK);

	}

	/*
	 * Status 204 NoContent sucesso
	 * Status 403 Forbiden acesso negado
	 * Status 404 Nao encontrado
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		clienteService.remover(id);
		return ResponseEntity.noContent().build();
	}

	/*
	 * Status 200 OK sucesso
	 * Status 403 Forbiden acesso negado
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDto>> buscaPaginada(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Page<Cliente> list = clienteService.buscaPaginada(page, linesPerPage, orderBy, direction);
		Page<ClienteDto> listDto = list.map(obj -> new ClienteDto(obj));

		return ResponseEntity.ok().body(listDto);
	}
}
