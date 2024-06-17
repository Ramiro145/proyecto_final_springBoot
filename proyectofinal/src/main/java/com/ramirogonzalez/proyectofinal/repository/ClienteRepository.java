package com.ramirogonzalez.proyectofinal.repository;

import com.ramirogonzalez.proyectofinal.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

}
