package merliontechs.web.rest;

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
        int a = precio.intValue();
        int b = this.precio.intValue()+a;
        this.precio=BigDecimal.valueOf(b);
    }

    public BigDecimal getPrecio(){
        return precio;
    }
}