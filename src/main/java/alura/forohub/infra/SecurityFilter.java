package alura.forohub.infra;

import alura.forohub.repository.UsuarioRepository;
import alura.forohub.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("El filtro está siendo llamado");

        // Excluir rutas específicas como /login
        String uri = request.getRequestURI();
        if ("/login".equals(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Obtención del token
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            throw new RuntimeException("Token no encontrado o vacío");
        }

        var token = authHeader.replace("Bearer ", "");
        var subject = tokenService.getSubject(token);

        if (subject != null) {
            // Token válido
            var usuario = usuarioRepository.findByCorreoElectronico(subject);
            if (usuario.isPresent()) {
                var authentication = new UsernamePasswordAuthenticationToken(
                        usuario.get(), null, usuario.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}