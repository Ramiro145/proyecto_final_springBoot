package com.ramirogonzalez.proyectofinal.controller;


import com.ramirogonzalez.proyectofinal.model.Producto;
import com.ramirogonzalez.proyectofinal.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    ProductoService productoService;


    @PostMapping("/productos/crear")
    public String saveProducto(@RequestBody Producto producto){
        productoService.saveProducto(producto);
        return "Producto creado correctamente";
    }

    @GetMapping("/productos")
    @ResponseBody
    public List<Producto> getProductos(){
        return productoService.getProductosList();
    }

    @GetMapping("/productos/{codigo_producto}")
    @ResponseBody
    public Producto getProducto(@PathVariable Long codigo_producto){
        return productoService.getProducto(codigo_producto);
    }

    @GetMapping("productos/falta_stock")
    @ResponseBody
    public List<Producto> getFaltaStock(){
        return productoService.getFaltaStock();
    }

    @DeleteMapping("productos/eliminar/{codigo_producto}")
    public String deleteProducto(@PathVariable Long codigo_producto){
        productoService.deleteProducto(codigo_producto);
        return "Producto eliminado correctamente";
    }

    @PutMapping("/productos/editar/{codigo_producto}")
    @ResponseBody
    public Producto editProducto(@PathVariable Long codigo_producto, @RequestBody Producto producto){
        return productoService.editProducto(codigo_producto,producto);
    }



}
