package com.concesionario.cursospring.service;

import com.concesionario.cursospring.entity.Cliente;
import com.concesionario.cursospring.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }
    public Optional <Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);

    }
    @Transactional
    public void delete(Long id){
        clienteRepository.deleteById(id);
    }

    @Transactional
    public Optional<Cliente>update(Cliente cliente, Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isPresent()) {
            Cliente clienteDb = clienteOptional.orElseThrow();

            clienteDb.setDNI(cliente.getDNI());
            clienteDb.setName(cliente.getName());
            clienteDb.setNumCompras(cliente.getNumCompras());
            return Optional.of(clienteRepository.save(clienteDb));
        }
        return clienteOptional;
    }
}
