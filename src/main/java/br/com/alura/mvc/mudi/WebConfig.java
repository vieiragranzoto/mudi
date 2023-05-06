package br.com.alura.mvc.mudi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import br.com.alura.mvc.mudi.interceptor.InterceptadorDeAcessos;
import br.com.alura.mvc.mudi.repository.AcessoRepository;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
	
	@Autowired
	private AcessoRepository acessoRepository; 
	
	@Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptadorDeAcessos(acessoRepository)).addPathPatterns("/**");
    }
}
