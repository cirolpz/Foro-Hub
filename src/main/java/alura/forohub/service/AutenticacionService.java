package alura.forohub.service;

import alura.forohub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService  implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        return usuarioRepository.findByCorreoElectronico(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));
    }
}
