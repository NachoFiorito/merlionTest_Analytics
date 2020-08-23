package merliontechs.web.rest;

import merliontechs.domain.Product;

public class ProdMasVend {
    
    Long producto;
    int cantVentas;

    public ProdMasVend (Long producto){
        cantVentas = 1;
        this.producto = producto;
    }

    public Long getProduct(){
        return producto;
    }

    public void sumarVenta(){
        this.cantVentas ++;
    }

    public int getCantVentas(){
        return cantVentas;
    }


}