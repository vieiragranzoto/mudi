package br.com.alura.mvc.mudi;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
    private DataSource dataSource;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
		.requestMatchers("/home/**").permitAll()
		.requestMatchers("/cadastrar").permitAll()
		.requestMatchers("/cadastro").permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.formLogin((form) -> form.loginPage("/login")
					.defaultSuccessUrl("/usuario/pedido", true)
					.permitAll())
		.logout(logout -> logout.logoutUrl("/logout")
				 .logoutSuccessUrl("/home"))
		.csrf().disable();

		return http.build();
	}
	
	@Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

	@Bean
	UserDetailsManager users() {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		return users;
	}

}