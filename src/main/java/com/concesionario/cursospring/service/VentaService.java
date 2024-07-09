package com.concesionario.cursospring.service;

import com.concesionario.cursospring.entity.Cliente;
import com.concesionario.cursospring.entity.Coche;
import com.concesionario.cursospring.entity.Venta;
import com.concesionario.cursospring.entity.enums.TipoPago;
import com.concesionario.cursospring.repository.ClienteRepository;
import com.concesionario.cursospring.repository.CocheRepository;
import com.concesionario.cursospring.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private CocheRepository cocheRepository;

    public List<Venta> finAll() {
        return ventaRepository.findAll();
    }


    public Optional<Venta> finById(Long id) {
        return ventaRepository.findById(id);
    }

    @Transactional
    public Venta save(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Transactional
    public void deleteById(Long id) {
        ventaRepository.deleteById(id);
    }


    @Transactional
    public void createVenta(Long Id, Long id, String Name, String Dni, TipoPago tipoPago,
                            Integer numCompras, Instant fecha, Double total) {

        Coche coche = cocheRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe el coche" + id));

        // Verificar si el coche ya está asignado a una venta
        if (coche.getVentas() != null && !coche.getVentas().isEmpty()) {
            throw new RuntimeException("El coche ya está asignado a una venta");
        }


        Cliente cliente;
        if (Id != null) {
            cliente = clienteRepository.findById(Id)
                    .orElseThrow(() -> new RuntimeException("Cliente not found"));
        } else {
            cliente = new Cliente();
            cliente.setName(Name);
            cliente.setDNI(Dni);
            cliente.setNumCompras(numCompras);
            clienteRepository.save(cliente);
        }

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setCoche(coche);
        venta.setFecha(LocalDate.now());
        venta.setTipoPago(tipoPago);
        venta.setTotal(total);
        ventaRepository.save(venta);

        //La relación entre el Coche y la Venta se establece agregando la Venta al conjunto de ventas del Cliente
        cocheRepository.save(coche);

    }

    @Transactional
    public Venta updateVenta(Venta venta, Long id) {
        Venta ventaDB = ventaRepository.findById(id).orElseThrow(()-> new RuntimeException("No existe el venta"));

        ventaDB.setFecha(venta.getFecha());
        ventaDB.setTotal(venta.getTotal());
        ventaDB.setTipoPago(venta.getTipoPago());

        if(!ventaDB.getCliente().getId().equals(venta.getCliente().getId())) {
            Cliente cliente = clienteRepository.save(venta.getCliente());
            ventaDB.setCliente(cliente);
        }
        if(!ventaDB.getCoche().getId().equals(venta.getCoche().getId())) {
            Coche coche = cocheRepository.save(venta.getCoche());
            ventaDB.setCoche(coche);
        }
        return ventaRepository.save(ventaDB);
    }
}
