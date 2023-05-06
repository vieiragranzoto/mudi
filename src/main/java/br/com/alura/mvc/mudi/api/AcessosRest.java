package br.com.alura.mvc.mudi.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.mvc.mudi.model.Acesso;
import br.com.alura.mvc.mudi.repository.AcessoRepository;

@RestController
@RequestMapping("/api/acessos")
public class AcessosRest {
	
	@Autowired
	private AcessoRepository acessoRepository; 
	
	@GetMapping
	public List<Acesso> getAcessos(){
		return acessoRepository.findAll();
	}
	
}
