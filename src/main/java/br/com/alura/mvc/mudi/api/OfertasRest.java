package br.com.alura.mvc.mudi.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.User;
import br.com.alura.mvc.mudi.repository.PedidoRepository;
import br.com.alura.mvc.mudi.repository.UserRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ofertas")
public class OfertasRest {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping
	public Oferta criaOferta(@Valid @RequestBody RequisicaoNovaOferta requisicao) {
		
		Optional<Pedido> pedidoBuscado = pedidoRepository.findById(requisicao.getPedidoId());
		if(!pedidoBuscado.isPresent()) {
			return null;
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user=userRepository.findByUsername(username);
		
		Pedido pedido = pedidoBuscado.get();
		
		Oferta nova = requisicao.toOferta();
		nova.setPedido(pedido);
		nova.setUser(user);
		pedido.getOfertas().add(nova);
		pedidoRepository.save(pedido);
		
		return nova;
	}
}
