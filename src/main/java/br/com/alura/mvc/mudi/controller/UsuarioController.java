package br.com.alura.mvc.mudi.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;
import br.com.alura.mvc.mudi.repository.OfertaRepository;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
	
	private final PedidoRepository pedidoRepository;
	private final OfertaRepository ofertaRepository;
	
	@Autowired
	public UsuarioController(PedidoRepository pedidoRepository, OfertaRepository ofertaRepository) {
		this.pedidoRepository=pedidoRepository;
		this.ofertaRepository = ofertaRepository;
	}
	
	@GetMapping("pedido/home")
	public String home(Model model, Principal principal) {
		List<Pedido> pedidos = pedidoRepository.findAllByUsuario(principal.getName());
		model.addAttribute("pedidos", pedidos);
		return "usuario/home";
	}

	@GetMapping("pedido/aguardando")
	public String aguardando(Model model, Principal principal) {
		List<Pedido> pedidos = pedidoRepository.findByStatusEUsuario(StatusPedido.AGUARDANDO, principal.getName());
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("status", "aguardando");
		return "usuario/home";
	}

	@GetMapping("pedido/ofertado")
	public String ofertado(Model model, Principal principal) {
		List<Pedido> pedidos = pedidoRepository.findByStatusEUsuario(StatusPedido.AGUARDANDO, principal.getName());
		List<Oferta> ofertas = new ArrayList<>();
		pedidos.forEach(pedido-> ofertas.addAll(pedido.getOfertas()));
		model.addAttribute("ofertas", ofertas);
		return "usuario/ofertado";
	}
	
	@PostMapping("pedido/aprovar")
	public String aprovar(@RequestParam("ofertaId") Long ofertaId) {
		Optional<Oferta> ofertaBuscada = ofertaRepository.findById(ofertaId);
		Oferta oferta = ofertaBuscada.get();
		if(oferta != null) {
			Pedido pedido = oferta.getPedido();
			pedido.setStatus(StatusPedido.APROVADO);
			pedido.setDataDaEntrega(oferta.getDataDaEntrega());
			pedido.setValor(oferta.getValor());
			pedidoRepository.save(pedido);
		}
		return "redirect:/usuario/pedido/aprovado";
	}
	
	@GetMapping("pedido/aprovado")
	public String aprovado(Model model, Principal principal) {
		List<Pedido> pedidos = pedidoRepository.findByStatusEUsuario(StatusPedido.APROVADO, principal.getName());
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("status", "aprovado");
		return "usuario/aprovado";
	}
	
	@PostMapping("pedido/recebido")
	public String recebido(@RequestParam("pedidoId") Long pedidoId) {
		Optional<Pedido> pedidoBuscada = pedidoRepository.findById(pedidoId);
		Pedido pedido = pedidoBuscada.get();
		if(pedido != null) {
			pedido.setStatus(StatusPedido.ENTREGUE);
			pedidoRepository.save(pedido);
		}
		return "redirect:/usuario/pedido/entregue";
	}

	@GetMapping("pedido/entregue")
	public String entregue(Model model, Principal principal) {
		List<Pedido> pedidos = pedidoRepository.findByStatusEUsuario(StatusPedido.ENTREGUE, principal.getName());
		model.addAttribute("pedidos", pedidos);
		model.addAttribute("status", "entregue");
		return "usuario/home";
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public String onError() {
		return "redirect:/usuario/home";
	}
}
