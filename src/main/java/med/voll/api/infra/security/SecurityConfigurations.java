package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // esta es una configuracion y la escanea por que es un prerequesito para que la aplicacion sea crea
@EnableWebSecurity // hibilda el modulo web security para esta configuracion
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    //con @EnableWebSecurity el metodo de abajo esta sedn complementado para sobrecribir el comportamiento de autenticacion
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity)
            throws Exception {
        //noinspection removal
        return  httpSecurity.csrf()
                .disable().sessionManagement()
                .sessionCreationPolicy
                        (SessionCreationPolicy.STATELESS)// le indicamoa s spring el tipo de sesion
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login")
                .permitAll()
                .requestMatchers("/swagger-ui.html", "/v3/api-docs/**","/swagger-ui/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(securityFilter,
                        UsernamePasswordAuthenticationFilter
                                .class)
                .build();
        //csrf() evita la sumplatacion de la seguridad pero para la autentiacion statedfull
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean//hace que este disponible en el contexto de spring
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
