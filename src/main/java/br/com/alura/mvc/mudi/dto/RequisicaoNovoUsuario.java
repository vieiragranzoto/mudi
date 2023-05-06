package br.com.alura.mvc.mudi.dto;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.alura.mvc.mudi.model.User;

public class RequisicaoNovoUsuario {
	
	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

	public User toUsuario() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder().encode(password));
		return user;
	}
}
