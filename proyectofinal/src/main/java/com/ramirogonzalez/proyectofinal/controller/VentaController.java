package com.ramirogonzalez.proyectofinal.controller;

import com.ramirogonzalez.proyectofinal.dto.VentaFechaDTO;
import com.ramirogonzalez.proyectofinal.dto.VentaMayorDTO;
import com.ramirogonzalez.proyectofinal.model.Producto;
import com.ramirogonzalez.proyectofinal.model.Venta;
import com.ramirogonzalez.proyectofinal.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class VentaController {

    @Autowired
    VentaService ventaService;

    @PostMapping("/ventas/crear")
    public String saveVenta(@RequestBody Venta venta){
        ventaService.saveVenta(venta);
        return "Venta creada exitosamente";
    }

    @GetMapping("/ventas")
    public List<Venta> getVentas(){
        return ventaService.getVentas();
    }

    @GetMapping("/ventas/{codigo_venta}")
    public Venta getVenta(@PathVariable Long codigo_venta){
        return ventaService.getVenta(codigo_venta);
    }

    @GetMapping("ventas/productos/{codigo_venta}")
    public List<Producto> getProductosVenta(@PathVariable Long codigo_venta){
        return ventaService.getVentasProducto(codigo_venta);
    }

    @GetMapping("ventas/fecha/{fecha_venta}")
    public VentaFechaDTO getFechaVenta(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha_venta){
        return ventaService.getFechaVenta(fecha_venta);
    }

    @GetMapping("ventas/mayor_venta")
    public VentaMayorDTO getMayorVenta(){
        return ventaService.getMayorVenta();
    }

    @DeleteMapping("ventas/eliminar/{codigo_venta}")
    public String deleteVenta(@PathVariable Long codigo_venta){
       ventaService.deleteVenta(codigo_venta);
       return "Venta eliminada correctamente";
    }

    @PutMapping("ventas/editar/{codigo_venta}")
    public Venta editVenta(@PathVariable Long codigo_venta, @RequestBody Venta venta){
        return ventaService.editVenta(codigo_venta,venta);
    }




}
