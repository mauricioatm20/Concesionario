package com.concesionario.cursospring.controller;

import com.concesionario.cursospring.entity.Coche;
import com.concesionario.cursospring.service.CocheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/coches")
public class CocheController {

    @Autowired
    public CocheService cocheService;

    @GetMapping("")
    public String getAll(ModelMap model) {

        List<Coche> coches = cocheService.findAll();
        model.put("coches", coches);
        model.put("view", "coche/coche_list");

        return "index/frame";
    }


    @GetMapping("/{id}")
    public String getFindById(Long id, ModelMap model) {
        Coche coche = cocheService.findById(id).orElseThrow(()-> new RuntimeException("Coche not found"));
        model.addAttribute("coche", coche);
        return "coche_detail";
    }

// Muestra el formulario para crear un nuevo vehículo.
    @GetMapping("/create")
    public String showcreate(ModelMap model) {
        model.put("view","coche/coche_form");
        model.addAttribute("coche", new Coche());
        return "index/frame";

    }

    //Crea un nuevo vehículo y redirige a la lista de vehículos.
    @PostMapping
    public String create(@ModelAttribute Coche coche) {
        cocheService.save(coche);
        return "redirect:/coches";

    }

    // Muestra el formulario para editar un vehículo existente.
    @GetMapping("/edit/{id}")
    public String update(@PathVariable Long id, ModelMap model){

        Coche coche= cocheService.findById(id).orElseThrow(()-> new RuntimeException("Coche not found"));
        model.put("view","coche/coche_form");
        model.addAttribute("coche", coche);
        return "index/frame";
    }

    //Actualiza el vehículo con los detalles proporcionados y redirige a la lista de vehículos.
    @PostMapping("/update/{id}")
    public String updateCoche(@PathVariable Long id, @ModelAttribute Coche coche_details) {
        cocheService.updateCoche(coche_details, id).orElseThrow(()->
                new RuntimeException("Coche not found"));
        return "redirect:/coches";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        cocheService.deleteById(id);
        return "redirect:/coches";
    }


}

