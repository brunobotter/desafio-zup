package br.com.bruno.banco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.bruno.banco.exception.AuthorizationException;
import br.com.bruno.banco.model.Conta;
import br.com.bruno.banco.model.enums.Role;
import br.com.bruno.banco.security.UserDetailsImpl;

@Service
public class UserService {
	
	@Autowired
	private ContaService contaService;
	
	public UserDetailsImpl getUserDetails() {
		try {
			return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean validaClienteConta(Conta conta) {
		UserDetailsImpl user = getUserDetails();
		
		if (user == null || !user.hasRole(Role.ADMIN) && !conta.getCliente().equals(user.getCliente())) {
			throw new AuthorizationException("Acesso negado");
		}
		if (user == null || !user.hasRole(Role.CLIENTE) && conta.getCliente().equals(user.getCliente())) {
			throw new AuthorizationException("Acesso negado");
		}
		return true;
	}

	public boolean validaClienteConta(Long key) {
		Conta conta = contaService.buscaPorId(key);
		return validaClienteConta(conta);		
	}

	public void validaClienteId(Long key) {
		UserDetailsImpl user = getUserDetails();

		if (user == null || !user.hasRole(Role.ADMIN) && !key.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}		
	}

	public void validaClienteCpf(String cpf) {
		UserDetailsImpl user = getUserDetails();

		if (user == null || !user.hasRole(Role.ADMIN) && !cpf.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}		
	}
	
	public boolean hasRole(Role perfil) {
		UserDetailsImpl user = getUserDetails();
		
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		return user.hasRole(perfil);
	}

}
