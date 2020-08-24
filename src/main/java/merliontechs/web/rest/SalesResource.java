package merliontechs.web.rest;

import merliontechs.domain.Sales;
import merliontechs.domain.enumeration.State;
import merliontechs.repository.SalesRepository;
import merliontechs.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map;
import java.util.Optional;
import java.time.*;



/**
 * REST controller for managing {@link merliontechs.domain.Sales}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SalesResource {

    private final Logger log = LoggerFactory.getLogger(SalesResource.class);

    private static final String ENTITY_NAME = "sales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesRepository salesRepository;

    public SalesResource(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    /**
     * {@code POST  /sales} : Create a new sales.
     *
     * @param sales the sales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sales, or with status {@code 400 (Bad Request)} if the sales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sales")
    public ResponseEntity<Sales> createSales(@RequestBody Sales sales) throws URISyntaxException {
        log.debug("REST request to save Sales : {}", sales);
        if (sales.getId() != null) {
            throw new BadRequestAlertException("A new sales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sales result = salesRepository.save(sales);
        return ResponseEntity.created(new URI("/api/sales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sales} : Updates an existing sales.
     *
     * @param sales the sales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sales,
     * or with status {@code 400 (Bad Request)} if the sales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sales")
    public ResponseEntity<Sales> updateSales(@RequestBody Sales sales) throws URISyntaxException {
        log.debug("REST request to update Sales : {}", sales);
        if (sales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sales result = salesRepository.save(sales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sales.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sales} : get all the sales.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sales in body.
     */
    @GetMapping("/sales")
    public List<Sales> getAllSales() {
        log.debug("REST request to get all Sales");
        return salesRepository.findAll();
    }

    /**
     * {@code GET  /sales/:id} : get the "id" sales.
     *
     * @param id the id of the sales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sales/{id}")
    public ResponseEntity<Sales> getSales(@PathVariable Long id) {
        log.debug("REST request to get Sales : {}", id);
        Optional<Sales> sales = salesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sales);
    }

    /**
     * {@code DELETE  /sales/:id} : delete the "id" sales.
     *
     * @param id the id of the sales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Void> deleteSales(@PathVariable Long id) {
        log.debug("REST request to delete Sales : {}", id);
        salesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    ///--- Realizo las funciones Java para cada item Pedido y ser mostrados en la API---///

    /**
     * {@code GET  /sales/VentasDeliveredPorDia} : get all the delivered sales per day.
     */
    @GetMapping("/sales/VentasDeliveredPorDia")
    public List<VentaFecha> VentasDeliveredPorDia(){
        log.debug("REST request to obtener las ventas con estado Delivered por dia");
        //* Uso la fecha como clave del map para verificar mas facil si hubo una venta el mismo dia
        Map<LocalDate, VentaFecha> VentaFechaMap = new TreeMap<LocalDate, VentaFecha>();
        List<Sales> ventas = getAllSales(); 


        //int cant =1;
        for (Sales venta: ventas){
           if (venta.getState() == State.DELIVERED){ 
               LocalDate date = venta.getDate();
                if (VentaFechaMap.containsKey(date)){
                    VentaFecha aux = VentaFechaMap.get(date);
                    aux.sumarVenta();
                    VentaFechaMap.put(date,aux);
                } 
                else {
                    VentaFechaMap.put(date, new VentaFecha(date));
                }
            } 
        } 
        List<VentaFecha> ventasDeliveredPorDia = new ArrayList<>(VentaFechaMap.values());

        return ventasDeliveredPorDia;
        
    }

    @GetMapping("/sales/VentasPorDia")
    public List<VentaFecha> VentasPorDia(){
        log.debug("REST request to obtener las ventas por dia");
        //* Idem funcion anterior por sin condicion por state
        Map<LocalDate, VentaFecha> VentaFechaMap = new TreeMap<LocalDate, VentaFecha>();
        List<Sales> ventas = getAllSales(); 


        for (Sales venta: ventas){
               LocalDate date = venta.getDate();
                if (VentaFechaMap.containsKey(date)){
                    VentaFecha aux = VentaFechaMap.get(date);
                    aux.sumarVenta();
                    VentaFechaMap.put(date,aux);
                } 
                else {
                    VentaFechaMap.put(date, new VentaFecha(date));
                }
            
        } 
        List<VentaFecha> ventasDeliveredPorDia = new ArrayList<>(VentaFechaMap.values());

        return ventasDeliveredPorDia;
        
    }

    @GetMapping("/sales/5ProductosMasVendidos")
    public List<ProdMasVend>  ProductosMasVendidos(){
        log.debug("REST request to obtener top 5 productos mas vendidos");
        Map<Long,ProdMasVend> prodVend = new TreeMap<Long,ProdMasVend>();
        List<Sales> ventas = getAllSales();

        for(Sales venta: ventas){
            Long idProd = venta.getProduct().getId();
            if(!prodVend.containsKey(idProd)){
                prodVend.put(idProd, new ProdMasVend(idProd));
            }else{
                ProdMasVend aux = prodVend.get(idProd);
                aux.sumarVenta();
                prodVend.put(idProd, aux);
            }
        }
        
        List<ProdMasVend> ProductosMasVendidos = new ArrayList<>(prodVend.values());

        return ProductosMasVendidos;
    }

    @GetMapping("/sales/5ProductosConMasIngresos")
    public List<ProdMasIngresos> ProductosConMasIngresos(){
        log.debug("REST request to obtener top 5 productos con mas ingresos");
        Map<Long, ProdMasIngresos> ProdIngr = new TreeMap<Long, ProdMasIngresos>();
        List<Sales> ventas = getAllSales();

        for(Sales venta: ventas){
            BigDecimal precioProd = venta.getProduct().getPrice();
            Long idProd = venta.getProduct().getId();
            if(!ProdIngr.containsKey(idProd)){
                ProdIngr.put(idProd, new ProdMasIngresos(idProd, precioProd));
            }else{
                ProdMasIngresos auxProd = ProdIngr.get(idProd);
                auxProd.sumarPrecio(precioProd);
                ProdIngr.put(idProd,auxProd);
            }
        }
        List<ProdMasIngresos> ProductosConMasIngresos = new ArrayList<>(ProdIngr.values());
       
        return ProductosConMasIngresos;
    }
    
}
