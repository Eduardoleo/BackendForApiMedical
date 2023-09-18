package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.usuarios.DatosAuntenticacionUsario;
import med.voll.api.domain.usuarios.Usuario;
import med.voll.api.infra.security.datosJWTToken;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutentiacionController {

    @Autowired
    private AuthenticationManager authenticationManager;// clase que inicia el preoceso de login

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity autenticarUsario(
            @RequestBody @Valid DatosAuntenticacionUsario
                    datosAuntenticacionUsario){
        Authentication authToken =
                new UsernamePasswordAuthenticationToken(
                datosAuntenticacionUsario.login(),
                datosAuntenticacionUsario.clave());
        var usuarioAutenticado =
                authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken(
                (Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new datosJWTToken(JWTtoken));
    }

}
