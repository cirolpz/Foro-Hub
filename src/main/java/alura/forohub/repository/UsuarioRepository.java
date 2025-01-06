package alura.forohub.repository;

import alura.forohub.entity.Usuario;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends ListCrudRepository<Usuario,Long> {
    Optional<Usuario> findById(Long id);
}
