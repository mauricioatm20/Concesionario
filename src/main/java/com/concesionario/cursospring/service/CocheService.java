package com.concesionario.cursospring.service;

import com.concesionario.cursospring.entity.Coche;
import com.concesionario.cursospring.repository.CocheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CocheService {

    @Autowired
    private CocheRepository cocheRepository;

    public List<Coche> findAll() {
        return cocheRepository.findAll();
    }

    public Optional<Coche> findById(Long id) {
        return cocheRepository.findById(id);
    }

    public Coche save(Coche coche) {
        return cocheRepository.save(coche);

    }
    public void deleteById(Long id) {
        cocheRepository.deleteById(id);

    }

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
