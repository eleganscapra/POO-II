package com.usil.enrollment.repository;
import com.usil.enrollment.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByEstudianteId(Long estudianteId);
    List<Matricula> findByEstudianteIdAndMatriculado(Long estudianteId, boolean matriculado);
}


