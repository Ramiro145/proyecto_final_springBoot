package com.ramirogonzalez.proyectofinal.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long codigo_venta;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha_venta;

    private Double total;

    @OneToMany(mappedBy = "venta")
    private List<Producto> listaProducto;

    @OneToOne
    @JoinColumn(name = "fk_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;


    public Venta() {
    }

    public Venta(Long codigo_venta, LocalDate fecha_venta, Double total, List<Producto> listaProducto, Cliente cliente) {
        this.codigo_venta = codigo_venta;
        this.fecha_venta = fecha_venta;
        this.total = total;
        this.listaProducto = listaProducto;
        this.cliente = cliente;
    }


}
