package com.concesionario.cursospring.controller;

import com.concesionario.cursospring.entity.Cliente;
import com.concesionario.cursospring.entity.Coche;
import com.concesionario.cursospring.entity.Venta;
import com.concesionario.cursospring.entity.enums.TipoPago;
import com.concesionario.cursospring.service.ClienteService;
import com.concesionario.cursospring.service.CocheService;
import com.concesionario.cursospring.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    public VentaService ventaService;

    @Autowired
    public ClienteService clienteService;

    @Autowired
    public CocheService cocheService;

    @GetMapping("")
    public String allVentas(ModelMap model) {

        List<Venta> ventas = ventaService.finAll();
        model.put("ventas", ventas);
        model.put("view","ventas/ventas_list");

        return "index/frame";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, ModelMap model) {
        Venta venta = ventaService.finById(id).orElseThrow(()-> new RuntimeException("Venta no encontrada"));
        model.addAttribute("venta", venta);
        return "ventas/venta_edit";
    }

    @GetMapping("/create")
    public String formCreate(ModelMap model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.findAll());

        // Obtener solo los coches disponibles
        List<Coche> cochesDisponibles = cocheService.findCochesDisponibles();
        model.addAttribute("coches", cochesDisponibles);

        model.addAttribute("tipos", TipoPago.values());
        model.put("view", "ventas/venta_form");
        return "index/frame";
    }

    @PostMapping("")
    public String create(@ModelAttribute Venta venta) {
        ventaService.save(venta);
        return "redirect:/ventas";
    }

    @GetMapping("/edit/{id}")
    public String update(@PathVariable Long id, ModelMap model) {
        Venta venta = ventaService.finById(id).orElseThrow(()-> new RuntimeException("Venta no encontrada"));
        model.addAttribute("venta", venta);
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("coches", cocheService.findAll());
        model.addAttribute("tipoPago", TipoPago.values());
        model.put("view","ventas/venta_form");
        return "index/frame";
    }


    @PostMapping("/update/{id}")
    public String updateVenta(@ModelAttribute Venta venta_update, @PathVariable Long id) {
        ventaService.updateVenta(venta_update, id);
        return "redirect:/ventas";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        ventaService.deleteById(id);
        return "redirect:/ventas";
    }

}
