package com.ramirogonzalez.proyectofinal.service;

import com.ramirogonzalez.proyectofinal.dto.VentaFechaDTO;
import com.ramirogonzalez.proyectofinal.dto.VentaMayorDTO;
import com.ramirogonzalez.proyectofinal.model.Producto;
import com.ramirogonzalez.proyectofinal.model.Venta;

import java.time.LocalDate;
import java.util.List;

public interface IVentaService {

    void saveVenta(Venta venta);

    List<Venta> getVentas();

    VentaFechaDTO getFechaVenta(LocalDate fecha_venta);

    Venta getVenta(Long codigo_venta);

    void deleteVenta(Long codigo_venta);

    Venta editVenta(Long codigo_venta, Venta venta);

    List<Producto> getVentasProducto(Long codigo_venta);

    VentaMayorDTO getMayorVenta();


}
