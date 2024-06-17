package com.ramirogonzalez.proyectofinal.service;

import com.ramirogonzalez.proyectofinal.dto.VentaFechaDTO;
import com.ramirogonzalez.proyectofinal.dto.VentaMayorDTO;
import com.ramirogonzalez.proyectofinal.model.Cliente;
import com.ramirogonzalez.proyectofinal.model.Producto;
import com.ramirogonzalez.proyectofinal.model.Venta;
import com.ramirogonzalez.proyectofinal.repository.ClienteRepository;
import com.ramirogonzalez.proyectofinal.repository.ProductoRepository;
import com.ramirogonzalez.proyectofinal.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VentaService implements IVentaService{

    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void saveVenta(Venta venta) {

        double total = 0.0;
        //settear cliente
        if(venta.getCliente() != null){

            Optional<Cliente> optionalCliente = clienteRepository.findById(venta.getCliente().getId_cliente());

            if(optionalCliente.isPresent()){

                Cliente clienteExis = optionalCliente.get();

                if(clienteExis.getVenta() != null && !clienteExis.getVenta().equals(venta)){
                    throw new IllegalStateException("El cliente ya esta asociado a otra venta");
                }

                clienteExis.setVenta(venta);
                venta.setCliente(clienteExis);

            }else{
                throw new NoSuchElementException("Cliente no encontrado con id: " + venta.getCliente().getId_cliente());
            }


        }else{
            throw new IllegalArgumentException("La venta debe tener un cliente asociado");
        }

        //settear productos
        if(venta.getListaProducto() != null && !venta.getListaProducto().isEmpty()){

                List<Producto> nuevosProductos = venta.getListaProducto().stream()
                        .map(producto -> {
                            //al proporcionar un conjunto con los objetos, se buscara el id de cada producto
                            Optional<Producto> optionalProducto = productoRepository.findById(producto.getCodigo_producto());

                            if(optionalProducto.isPresent()){

                                Producto productoExis = optionalProducto.get();


                                productoExis.setVenta(venta); //relacion bidireccional

                                return productoExis;

                            }else{
                                throw new NoSuchElementException("No hay productos con id: " + producto.getCodigo_producto());
                            }

                        }).collect(Collectors.toList());



                for(Producto productoValor : nuevosProductos){
                    total = total + productoValor.getCosto();
                }
                venta.setTotal(total);

        }else{
            venta.setListaProducto(null);
            venta.setTotal(0.0);
        }

        ventaRepository.save(venta);
    }

    @Override
    public List<Venta> getVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public VentaFechaDTO getFechaVenta(LocalDate fecha_venta) {

        double montoTotal = 0.0;
        List<Venta>  listaVentas = ventaRepository.getVentasFecha(fecha_venta);

        VentaFechaDTO ventaFechaDTO = new VentaFechaDTO();
        ventaFechaDTO.setFecha_dia(fecha_venta);
        ventaFechaDTO.setCantidadVentas(listaVentas.size());

        for(Venta venta : listaVentas){
            montoTotal = montoTotal + venta.getTotal();
        }
        ventaFechaDTO.setMontoTotalDia(montoTotal);

        return ventaFechaDTO;
    }

    @Override
    public Venta getVenta(Long codigo_venta) {
        return ventaRepository.findById(codigo_venta).orElseThrow(()-> new NoSuchElementException("No hay venta con ese id: " + codigo_venta));
    }

    @Override
    public void deleteVenta(Long codigo_venta) {

        Optional<Venta> optionalVenta = ventaRepository.findById(codigo_venta);
        if (optionalVenta.isPresent()) {
            Venta venta = optionalVenta.get();

            // Desasociar y eliminar todos los productos asociados a la venta
            if (venta.getListaProducto() != null) {
                for(Producto producto : venta.getListaProducto()){
                    producto.setVenta(null);
                    productoRepository.save(producto);
                }
            }

            // Desasociar la venta del cliente
            Cliente cliente = venta.getCliente();
            if (cliente != null) {
                cliente.setVenta(null);
                clienteRepository.save(cliente);
            }

            // Finalmente, eliminar la venta
            ventaRepository.delete(venta);

        } else {
            throw new NoSuchElementException("Venta no encontrada con id: " + codigo_venta);
        }

        ventaRepository.deleteById(codigo_venta);
    }

    @Override
    public Venta editVenta(Long codigo_venta, Venta venta) {

        double nuevoTotal = 0.0;

        Optional<Venta> existingVentaOptional = ventaRepository.findById(codigo_venta);

        if(existingVentaOptional.isPresent()){
            Venta existingVenta = existingVentaOptional.get();

            //actualizamos datos generales
            existingVenta.setFecha_venta(venta.getFecha_venta());


            //manejo de relacion con cliente
            if(venta.getCliente() != null){

                Cliente clienteExis  = clienteRepository
                        .findById(venta.getCliente().getId_cliente())
                        .orElseThrow(()-> new NoSuchElementException("No hay cliente con ese id: " + venta.getCliente().getId_cliente()));

                existingVenta.setCliente(clienteExis);

            }else{
                existingVenta.setCliente(null);
            }


            //manejamos relaciones con los productos
            if(venta.getListaProducto() != null){

                for(Producto producto : existingVenta.getListaProducto()){
                        producto.setVenta(null);
                        productoRepository.save(producto);
                }

                //vincular nuevos productos si la lista no esta vacia

                if(!venta.getListaProducto().isEmpty()){

                    List<Producto> nuevosProductos = venta.getListaProducto().stream()
                            .map(producto -> {
                                //al proporcionar un conjunto con los objetos, se buscara el id de cada producto
                                Optional<Producto> optionalProducto = productoRepository.findById(producto.getCodigo_producto());

                                if(optionalProducto.isPresent()){

                                    Producto productoNuevo = optionalProducto.get();


                                    productoNuevo.setVenta(existingVenta); //relacion bidireccional

                                    return productoNuevo;

                                }else{
                                    throw new NoSuchElementException("No hay productos con id: " + producto.getCodigo_producto());
                                }

                            }).collect(Collectors.toList());


                    for(Producto productoValores : nuevosProductos){
                        nuevoTotal = nuevoTotal + productoValores.getCosto();
                    }

                    existingVenta.setTotal(nuevoTotal);
                    existingVenta.setListaProducto(nuevosProductos);

                }else{
                    existingVenta.setListaProducto(null);
                    existingVenta.setTotal(0.0);
                }

            }

            ventaRepository.save(existingVenta);
            return existingVenta;

        }else{
            throw new NoSuchElementException("No existe una venta con ese id: " + codigo_venta);
        }

    }

    @Override
    public List<Producto> getVentasProducto(Long codigo_venta) {
        Venta ventaExis = this.getVenta(codigo_venta);
        return ventaExis.getListaProducto();
    }

    @Override
    public VentaMayorDTO getMayorVenta() {

        Venta ventaMayorExis = ventaRepository.getMayorVenta();

        VentaMayorDTO ventaMayorDTO = new VentaMayorDTO();

        ventaMayorDTO.setCodigo_venta(ventaMayorExis.getCodigo_venta());
        ventaMayorDTO.setTotal(ventaMayorExis.getTotal());
        ventaMayorDTO.setCantidad_productos(ventaMayorExis.getListaProducto().size());
        ventaMayorDTO.setNombre_cliente(ventaMayorExis.getCliente().getNombre());
        ventaMayorDTO.setApellido_cliente(ventaMayorExis.getCliente().getApellido());

        return ventaMayorDTO;

    }


}
