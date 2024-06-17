package com.ramirogonzalez.proyectofinal.service;

import com.ramirogonzalez.proyectofinal.model.Producto;
import com.ramirogonzalez.proyectofinal.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductoService implements IProductoService{


    @Autowired
    ProductoRepository productoRepository;

    @Override
    public void saveProducto(Producto producto) {
        productoRepository.save(producto);
    }

    @Override
    public List<Producto> getProductosList() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProducto(Long idProducto) {
        return productoRepository.findById(idProducto).orElseThrow(()->new NoSuchElementException("No hay producto con id: " + idProducto));
    }

    @Override
    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto editProducto(Long idProducto, Producto producto) {

        Producto productoExist = this.getProducto(idProducto);

        productoExist.setNombre(producto.getNombre());
        productoExist.setMarca(producto.getMarca());
        productoExist.setCosto(producto.getCosto());
        productoExist.setCantidad_disponible(producto.getCantidad_disponible());

        productoRepository.save(productoExist);

        return productoExist;

    }

    @Override
    public List<Producto> getFaltaStock() {
        return productoRepository.findFaltaStock();
    }
}
