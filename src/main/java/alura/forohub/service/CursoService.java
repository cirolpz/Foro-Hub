package alura.forohub.service;

import alura.forohub.dto.CursoRequestDTO;
import alura.forohub.dto.CursoResponseDTO;
import alura.forohub.entity.Curso;
import alura.forohub.repository.CursoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CursoService {
    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public CursoResponseDTO registrar(CursoRequestDTO cursoDTO) {
        Curso nuevoCurso = new Curso();
        nuevoCurso.setNombre(cursoDTO.nombre());
        nuevoCurso.setCategoria(cursoDTO.categoria());
        Curso cursoGuardado = cursoRepository.save(nuevoCurso);
        return convertirACursoDTO(cursoGuardado);
    }

    public List<CursoResponseDTO> listarTodos() {
        return cursoRepository.findAll()
                .stream()
                .map(this::convertirACursoDTO)
                .toList();
    }

    public CursoResponseDTO buscarPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));
        return convertirACursoDTO(curso);
    }

    public CursoResponseDTO actualizarPorId(Long id, CursoRequestDTO cursoDTO) {
        validarExistenciaCurso(id);
        Curso cursoExistente = cursoRepository.findById(id).orElseThrow();
        cursoExistente.setNombre(cursoDTO.nombre());
        cursoExistente.setCategoria(cursoDTO.categoria());
        Curso cursoActualizado = cursoRepository.save(cursoExistente);
        return convertirACursoDTO(cursoActualizado);
    }

    public void eliminarCurso(Long id) {
        validarExistenciaCurso(id);
        cursoRepository.deleteById(id);
    }

    public void validarExistenciaCurso(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado");
        }
    }

    private CursoResponseDTO convertirACursoDTO(Curso curso) {
        return new CursoResponseDTO(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}