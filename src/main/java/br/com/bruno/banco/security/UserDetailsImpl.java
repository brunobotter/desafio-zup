package br.com.bruno.banco.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.bruno.banco.model.Cliente;
import br.com.bruno.banco.model.enums.Role;

public class UserDetailsImpl implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String cpf;
	private String senha;
	private String nome;

	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(Cliente cliente) {
		this.id = cliente.getClienteId();
		this.cpf = cliente.getCpf();
		this.senha = cliente.getSenha();
		this.authorities = cliente.getPerfis().stream().map(
				p -> new SimpleGrantedAuthority(p.getDescricao())).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}
	
	public Cliente getCliente() {
		return new Cliente(id, nome,cpf, senha);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return cpf;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean hasRole(Role perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}


}
