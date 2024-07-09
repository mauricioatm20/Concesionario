package com.concesionario.cursospring.service;

import com.concesionario.cursospring.entity.Coche;
import com.concesionario.cursospring.repository.CocheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CocheService {

    @Autowired
    private CocheRepository cocheRepository;

    public List<Coche> findAll() {
        return cocheRepository.findAll();
    }

    public List<Coche> findCochesDisponibles() {
        List<Coche> coches = cocheRepository.findAll();

        // Filtrar coches que no están asociados a ninguna venta
        List<Coche> cochesDisponibles = coches.stream()
                .filter(coche -> coche.getVentas() == null || coche.getVentas().isEmpty())
                .collect(Collectors.toList());

        return cochesDisponibles;
    }

    public Optional<Coche> findById(Long id) {
        return cocheRepository.findById(id);
    }

    @Transactional
    public Coche save(Coche coche) {
        return cocheRepository.save(coche);

    }
    @Transactional
    public void deleteById(Long id) {
        cocheRepository.deleteById(id);

    }

    @Transactional
    public Optional<Coche> updateCoche(Coche coche, Long id) {
        Optional<Coche> cocheOptional = cocheRepository.findById(id);
        if (cocheOptional.isPresent()) {
            Coche cocheDb = cocheOptional.orElseThrow();

            cocheDb.setMarca(coche.getMarca());
            cocheDb.setModelo(coche.getModelo());
            cocheDb.setColor(coche.getColor());
            cocheDb.setPrecio(coche.getPrecio());
            return Optional.of(cocheRepository.save(cocheDb));
        }
        return cocheOptional;
    }


}
