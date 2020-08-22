package merliontechs.web.rest;

import java.time.*;
/*
* Clase de sales custom con abstraccion de los datos mas necesarios con
*  sus Getters 
*/
public class VentaFecha {
    int cantVentas;
    LocalDate fecha;
    

    public VentaFecha(LocalDate fecha) {
        cantVentas = 1;
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }


    public void sumarVenta() {
        this.cantVentas ++;
    }

    public int getCantidadVentas() {
        return cantVentas;
    }

}
