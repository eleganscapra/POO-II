package com.usil.enrollment.repository;
import com.usil.enrollment.model.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long> {
    List<Seccion> findByCursoId(Long cursoId);
    List<Seccion> findByCursoIdAndDocenteId(Long cursoId, Long docenteId);
    List<Seccion> findByDocenteIdAndDiaSemana(Long docenteId, String diaSemana);
}

