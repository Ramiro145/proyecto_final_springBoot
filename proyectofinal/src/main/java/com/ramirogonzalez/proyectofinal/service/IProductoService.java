package com.ramirogonzalez.proyectofinal.service;

import com.ramirogonzalez.proyectofinal.model.Producto;

import java.util.List;

public interface IProductoService {

    void saveProducto(Producto producto);

    List<Producto> getProductosList();

    Producto getProducto(Long idProducto);

    void deleteProducto(Long id);

    Producto editProducto(Long idProducto, Producto producto);

    //obtener productos cuya cantidad sea menor a 5
    List<Producto> getFaltaStock();

}
