package alura.forohub.repository;

import alura.forohub.entity.Curso;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CursoRepository extends ListCrudRepository<Curso,Long> {
    Optional<Curso> findById(Long id);
}
