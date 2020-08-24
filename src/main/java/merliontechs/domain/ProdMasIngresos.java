package merliontechs.domain;

import java.math.BigDecimal;

public class ProdMasIngresos {
    
    BigDecimal precio;
    Long Producto;

    public ProdMasIngresos(Long producto, BigDecimal precio){
        this.Producto = producto;
        this.precio = precio;
    }

    public Long getProducto(){
        return Producto;
    }

    public void sumarPrecio(BigDecimal precio){
        this.precio.add(precio) ;
    }

    public BigDecimal getPrecio(){
        return precio;
    }
}