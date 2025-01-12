package alura.forohub.service;

import alura.forohub.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    public String generarToken(Usuario usuario){
        try {
            String secret = System.getenv("JWT_SECRET");
            if (secret == null || secret.isEmpty()) {
                throw new RuntimeException("Secret key not found in environment variables");
            }
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return  JWT.create()
                    .withIssuer("portalweb")
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error creating JWT", exception);
        }
    }

    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-07:00"));
    }

    public String getSubject(String token){
        if (token == null){
            throw new RuntimeException("Token cannot be null");
        }
        DecodedJWT verifier = null;
        try {
            String secret = System.getenv("JWT_SECRET");  // Accediendo a la variable de entorno
            if (secret == null || secret.isEmpty()) {
                throw new RuntimeException("Secret key not found in environment variables");
            }
            Algorithm algorithm = Algorithm.HMAC256(secret);  // Usando la variable de entorno
            verifier = JWT.require(algorithm)
                    .withIssuer("portalweb")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception){
            System.out.println(exception.toString());
            throw new RuntimeException("Token verification failed", exception);
        }
        if (verifier == null || verifier.getSubject() == null) {
            throw new RuntimeException("Verifier Invalid");
        }
        return verifier.getSubject();
    }
}
