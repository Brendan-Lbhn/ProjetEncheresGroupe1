package fr.eni.groupe1.encheres.security;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import javax.sql.DataSource;



@Configuration
@EnableWebSecurity
public class ApplicationSecurity {

    @Autowired
    private DataSource dataSource ;

    private final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder() ;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
        auth.jdbcAuthentication()
            .dataSource( dataSource )
            .usersByUsernameQuery( "SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo = ? " )
            .authoritiesByUsernameQuery( "SELECT ?, 'admin' " )
            .passwordEncoder( passwordEncoder )
            ;
    }

//*
    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .anyRequest()
            .permitAll()
//            .authenticated()
            .and().formLogin(Customizer.withDefaults())
            ;

        return http.build();
    }
// */

    /*
    private final PasswordEncoder passwordEncoder  = new PasswordEncoder() {

    @Override
    public String encode(CharSequence rawPassword) {
        System.out.println( "encode: " + rawPassword);
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println( "matches: " + rawPassword + " " + encodedPassword );
        if ( encodedPassword.startsWith("{noop}") ) {
            return encodedPassword.endsWith(rawPassword.toString());
        } else {
            var result = new BCryptPasswordEncoder().matches( rawPassword, encodedPassword ) ;
            System.out.println(result);
            return result ;
        }
    }
} ;
*/

//    @Autowired
//    private UserDetailsService userDetailsService;

/*    @Bean
    public DaoAuthenticationProvider authProvider() {
        final var authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
*/
/*    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider())
                .build();
    }
*/
}

