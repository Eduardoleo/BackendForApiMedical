package med.voll.api.infra.security;

import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service//escenea la clasd como un servicio de la aplicacion
public class AutenticacionSevice implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override    //carga usuario por username
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findBylogin(username);
    } // clase propia de spring para la autenticacion del usarion
}
