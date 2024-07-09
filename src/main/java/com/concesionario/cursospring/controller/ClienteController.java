package com.concesionario.cursospring.controller;

import com.concesionario.cursospring.entity.Cliente;
import com.concesionario.cursospring.service.ClienteService;
import com.concesionario.cursospring.service.CocheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private CocheService cocheService;

    @GetMapping("")
    public String getAllClientes(ModelMap model) {

        List<Cliente> clienteList = clienteService.findAll();
        model.put("clientes", clienteList);
        model.put("view","cliente/clientes_list");
        return "index/frame";
    }

    @GetMapping("/detail/{id}")
    public String getFindById(Long id, ModelMap model) {
        Cliente cliente = clienteService.findById(id).orElseThrow(()-> new RuntimeException ("Cliente not foud"));
        model.addAttribute("cliente", cliente);
        return "cliente_detail";
    }

    @GetMapping("/create")
    public String formCreate(ModelMap model){
        model.addAttribute("cliente", new Cliente());
        model.put("view","cliente/cliente_form");
        return "index/frame";

    }

    @PostMapping
    public String create(@ModelAttribute Cliente cliente){
        clienteService.save(cliente);
        return "redirect:/clientes";

    }

    @GetMapping("/edit/{id}")
    public String formUpdate(ModelMap model, @PathVariable Long id){
        Cliente cliente = clienteService.findById(id).orElseThrow(()-> new RuntimeException("Cliente not foud"));
         model.addAttribute("cliente", cliente);
         model.put("view","cliente/cliente_form");
         return "index/frame";
    }

    @PostMapping("/update/{id}")
    public String updateCliente(@PathVariable Long id, @ModelAttribute Cliente cliente_up){
        clienteService.update(cliente_up, id).orElseThrow(()-> new RuntimeException("Cliente not foud"));
        return "redirect:/clientes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        clienteService.delete(id);
        return "redirect:/clientes";
    }
}
