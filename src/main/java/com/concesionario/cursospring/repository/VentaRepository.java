package com.concesionario.cursospring.repository;

import com.concesionario.cursospring.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}
