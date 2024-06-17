package com.ramirogonzalez.proyectofinal.service;

import com.ramirogonzalez.proyectofinal.model.Cliente;
import com.ramirogonzalez.proyectofinal.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class ClienteService implements IClienteService{

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void saveCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getCliente(Long idCliente) {
        return clienteRepository.findById(idCliente).orElseThrow(()->new NoSuchElementException("No hay cliente con id: " + idCliente));
    }

    @Override
    public void deleteCliente(Long idCliente) {
        clienteRepository.deleteById(idCliente);
    }

    @Override
    public Cliente editCliente(Long idCliente, Cliente cliente) {

        Cliente clienteExist = this.getCliente(idCliente);

        clienteExist.setNombre(cliente.getNombre());
        clienteExist.setApellido(cliente.getApellido());
        clienteExist.setDni(cliente.getDni());

        clienteRepository.save(clienteExist);

        return clienteExist;

    }


}
