package br.com.bruno.banco.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bruno.banco.model.Cliente;
import br.com.bruno.banco.repository.ClienteRepository;
import br.com.bruno.banco.security.UserDetailsImpl;
@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		Optional<Cliente> cliente = clienteRepository.buscarPorCpf(cpf);
		
		return new UserDetailsImpl(cliente.orElseThrow(() -> new UsernameNotFoundException(cpf)));
	}

}
