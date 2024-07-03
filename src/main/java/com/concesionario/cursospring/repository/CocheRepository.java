package com.concesionario.cursospring.repository;

import com.concesionario.cursospring.entity.Coche;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CocheRepository extends JpaRepository<Coche, Long> {
}
