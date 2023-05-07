package br.com.alura.mvc.mudi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.alura.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.OfertaRepository;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("/oferta")
public class OfertaController {
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private OfertaRepository ofertaRepository;
	
	@GetMapping
	@RequestMapping("home")
	public String getFormularioParaOfertas() {
		return "oferta/home";
	}
	
	@PostMapping
	@RequestMapping("aprovada")
	public String setPedidoAprovado(@RequestParam("ofertaId") Long ofertaId) {
		Optional<Oferta> ofertaBuscada = ofertaRepository.findById(ofertaId);
		Oferta oferta = ofertaBuscada.get();
		if(oferta != null) {
			Pedido pedido = oferta.getPedido();
			pedido.setStatus(StatusPedido.APROVADO);
			pedidoRepository.save(pedido);
		}
		return "redirect:/usuario/pedido/aprovado";
	}
}
