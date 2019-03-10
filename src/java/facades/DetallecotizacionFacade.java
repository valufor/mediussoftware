/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entidades.Cotizacion;
import entidades.Detallecotizacion;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Arco02
 */
@Stateless
public class DetallecotizacionFacade extends AbstractFacade<Detallecotizacion> {

    @PersistenceContext(unitName = "MediusSoftwareV3PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetallecotizacionFacade() {
        super(Detallecotizacion.class);
    }
    
    
    public List<Detallecotizacion> buscaCodigo(Cotizacion cotizacion) {
        try {
            List<Detallecotizacion> listaCodigodet;
            Query query = em.createQuery("SELECT d FROM Detallecotizacion d WHERE  d.cotizacionid = :var1");
            query.setParameter("var1", cotizacion.getIdcotizacion());
            listaCodigodet = query.getResultList();
            if (!listaCodigodet.isEmpty()) {
                return listaCodigodet;
            }
        } catch (Exception e) {
        }
        return null;
    }
    
}
