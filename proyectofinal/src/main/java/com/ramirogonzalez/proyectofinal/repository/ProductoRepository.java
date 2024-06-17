package com.ramirogonzalez.proyectofinal.repository;

import com.ramirogonzalez.proyectofinal.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query(value = "SELECT * FROM producto WHERE cantidad_disponible < 5", nativeQuery = true)
    List<Producto> findFaltaStock();

}
