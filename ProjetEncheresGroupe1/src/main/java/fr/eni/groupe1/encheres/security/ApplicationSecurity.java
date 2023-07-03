package fr.eni.groupe1.encheres.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity {

	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private DataSource dataSource;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Bean
	public PasswordEncoder passwordEncoder() {
		return passwordEncoder;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo = ? ")
				.authoritiesByUsernameQuery("SELECT ?, 'admin' ").passwordEncoder(passwordEncoder);
	}

//*
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth
					// Permettre aux visiteurs d'accéder à la page d'accueil
					.requestMatchers(HttpMethod.GET, "/").permitAll()
					// Permettre aux visiteurs d'accéder a la page de creation de profil
					.requestMatchers(HttpMethod.GET, "/CreationProfil").permitAll()
					// Permettre aux visiteurs d'accéder a la confirmation d'envoi
					.requestMatchers(HttpMethod.POST, "/CreationProfil/Suite").permitAll()
					// Accès à la page de connexion
					//.requestMatchers("/login").permitAll()
					// Accès à la vue principale
					.requestMatchers("/accueil").permitAll()
					// Permettre à tous d'afficher correctement les images et CSS
					.requestMatchers("/styles/*").permitAll()
					.requestMatchers("/images/*").permitAll()
					// Il faut être connecté pour toutes autres URLs
					.anyRequest().authenticated();	
		});
		//http.formLogin(Customizer.withDefaults());
		
		http.formLogin(form -> {
			form
			.loginPage("/login")
			.defaultSuccessUrl("/")
			.usernameParameter("pseudo")
			.passwordParameter("motDePasse")
			.permitAll();
		});
		
		http.logout(logout -> 
		logout
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.deleteCookies("JSESSIONID")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/").permitAll());
		
		
		return http.build();
		
		
	}
	
	
	
}
