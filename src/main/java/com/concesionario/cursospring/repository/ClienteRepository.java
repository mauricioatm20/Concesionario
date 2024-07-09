package com.concesionario.cursospring.repository;

import com.concesionario.cursospring.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
