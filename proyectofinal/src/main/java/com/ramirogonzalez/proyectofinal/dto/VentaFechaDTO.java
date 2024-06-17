package com.ramirogonzalez.proyectofinal.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class VentaFechaDTO {

    private LocalDate fecha_dia;
    private Double montoTotalDia;
    private Integer cantidadVentas;

    public VentaFechaDTO() {
    }
    public VentaFechaDTO(LocalDate fecha_dia, Double montoTotalDia, Integer cantidadVentas) {
        this.fecha_dia = fecha_dia;
        this.montoTotalDia = montoTotalDia;
        this.cantidadVentas = cantidadVentas;
    }

}
