package br.com.alura.mvc.mudi.interceptor;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.alura.mvc.mudi.model.Acesso;
import br.com.alura.mvc.mudi.repository.AcessoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InterceptadorDeAcessos implements HandlerInterceptor {

	private final AcessoRepository acessoRepository; 
	
	@Autowired
	public InterceptadorDeAcessos(AcessoRepository acessoRepository) {
	    this.acessoRepository = acessoRepository;
	}
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	
        Acesso acesso = new Acesso();
        acesso.setPath(request.getRequestURI());
        acesso.setData(LocalDateTime.now());

        request.setAttribute("acesso", acesso);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	
        Acesso acesso = (Acesso) request.getAttribute("acesso");
        acesso.setDuracao(Duration.between(acesso.getData(), LocalDateTime.now()));
        acessoRepository.save(acesso);
    }
}
