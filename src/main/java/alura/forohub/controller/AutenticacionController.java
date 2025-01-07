package alura.forohub.controller;

import alura.forohub.dto.AutenticacionDTO;
import alura.forohub.dto.UsuarioDTO;
import alura.forohub.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid AutenticacionDTO autenticacionDTO){
        try {
            Authentication token = new UsernamePasswordAuthenticationToken(
                    autenticacionDTO.correoElectronico(),
                    autenticacionDTO.password()
            );
            authenticationManager.authenticate(token);
            return ResponseEntity.ok("Autenticación exitosa");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en la autenticación: " + e.getMessage());
        }
    }
}