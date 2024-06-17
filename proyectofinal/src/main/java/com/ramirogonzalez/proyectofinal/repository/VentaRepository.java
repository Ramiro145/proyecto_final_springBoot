package com.ramirogonzalez.proyectofinal.repository;

import com.ramirogonzalez.proyectofinal.model.Producto;
import com.ramirogonzalez.proyectofinal.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Long> {


    @Query(value = "SELECT * FROM venta WHERE fecha_venta = :fecha_venta" ,nativeQuery = true)
    List<Venta> getVentasFecha(@Param("fecha_venta")LocalDate fecha_venta);


    @Query(value = "SELECT * FROM venta ORDER BY total DESC LIMIT 1" ,nativeQuery = true)
    Venta getMayorVenta();


}
