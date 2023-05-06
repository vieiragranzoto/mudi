package br.com.alura.mvc.mudi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.mvc.mudi.dto.RequisicaoNovoUsuario;
import br.com.alura.mvc.mudi.model.Authority;
import br.com.alura.mvc.mudi.model.User;
import br.com.alura.mvc.mudi.repository.AuthorityRepository;
import br.com.alura.mvc.mudi.repository.UserRepository;

@Controller
public class CadastroUsuarioController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@GetMapping
	@RequestMapping("/cadastrar")
	public String cadastrar() {
		return "cadastrar-usuario";
	}
	
	@PostMapping("cadastro")
	public String cadastroDeUsuario(RequisicaoNovoUsuario requisicaoNovoUsuario) {
		User user = requisicaoNovoUsuario.toUsuario();
		user.setEnabled(true);
		Authority authority = new Authority();
		authority.setUsername(user.getUsername());
		authority.setUser(user);
		authority.setAuthority("USER");
		userRepository.save(user);
		authorityRepository.save(authority);
		return "login";
	}
}
