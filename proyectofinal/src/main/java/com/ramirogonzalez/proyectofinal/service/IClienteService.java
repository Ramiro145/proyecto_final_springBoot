package com.ramirogonzalez.proyectofinal.service;


import com.ramirogonzalez.proyectofinal.model.Cliente;

import java.util.List;

public interface IClienteService {

    void saveCliente(Cliente cliente);

    List<Cliente> getClientes();

    Cliente getCliente(Long idCliente);

    void deleteCliente(Long idCliente);

    Cliente editCliente(Long idCliente, Cliente cliente);


}
