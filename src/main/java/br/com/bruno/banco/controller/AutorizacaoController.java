package br.com.bruno.banco.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bruno.banco.dto.JwtResponseDto;
import br.com.bruno.banco.dto.LoginDto;
import br.com.bruno.banco.exception.AuthorizationException;
import br.com.bruno.banco.repository.impl.ClienteRepositoryImpl;
import br.com.bruno.banco.security.JwtTokenProvider;

@RestController
@RequestMapping(value = "/auth")
public class AutorizacaoController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private ClienteRepositoryImpl userRepository;
	
	/*  cpf e senha para login e ira gerar o token que deve ser adicionado ao header 
	 * 
	 * */

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginDto data){
		try {
			var username = data.getCpf();
			var pasword = data.getSenha();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pasword));
			
			var user = userRepository.buscarPorCpf(username);
			 
			var token = "";
			
			if (user != null) {
				token = tokenProvider.createToken(username, user.get().getPerfis());
			} else {
				throw new AuthorizationException("Usuario nao encontrado!");
			}
		
			return ResponseEntity.ok(new JwtResponseDto(token));
		}catch (RuntimeException e) {
			throw new AuthorizationException("Usuario ou senha invalida!");	
		}
	}
	

}
