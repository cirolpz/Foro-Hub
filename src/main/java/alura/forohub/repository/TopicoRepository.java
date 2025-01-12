package alura.forohub.repository;

import alura.forohub.entity.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTitleAndMessage(String title, String message);

    Page<Topico> findAll(Specification<Topico> spec, Pageable paginacion);
}
