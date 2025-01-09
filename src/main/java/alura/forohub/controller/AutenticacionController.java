package alura.forohub.controller;

import alura.forohub.dto.AutenticacionDTO;
import alura.forohub.dto.DatosJWTToken;
import alura.forohub.entity.Usuario;
import alura.forohub.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;



    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid AutenticacionDTO autenticacionDTO){
        try {
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    autenticacionDTO.correoElectronico(),
                    autenticacionDTO.password()
            );
            var usuarioAutenticado= authenticationManager.authenticate((authToken));
            var JWTtoken = tokenService.generarToken((Usuario)usuarioAutenticado.getPrincipal());
            authenticationManager.authenticate(authToken);
            return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en la autenticación: " + e.getMessage());
        }
    }
}