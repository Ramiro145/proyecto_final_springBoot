package com.ramirogonzalez.proyectofinal.controller;

import com.ramirogonzalez.proyectofinal.model.Cliente;
import com.ramirogonzalez.proyectofinal.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping("clientes/crear")
    public String saveCliente(@RequestBody Cliente cliente){
        clienteService.saveCliente(cliente);
        return "Cliente creado correctamente";
    }

    @GetMapping("/clientes")
    @ResponseBody
    public List<Cliente> getClientes(){
        return clienteService.getClientes();
    }

    @GetMapping("clientes/{id_cliente}")
    @ResponseBody
    public Cliente getCliente(@PathVariable Long id_cliente){
        return clienteService.getCliente(id_cliente);
    }

    @DeleteMapping("/clientes/eliminar/{id_cliente}")
    public String deleteCliente(@PathVariable Long id_cliente){
        clienteService.deleteCliente(id_cliente);
        return "Cliente eliminado correctamente";
    }

    @PutMapping("clientes/editar/{id_cliente}")
    @ResponseBody
    public Cliente editCliente(@PathVariable Long id_cliente, @RequestBody Cliente cliente){
        return clienteService.editCliente(id_cliente,cliente);
    }


}
