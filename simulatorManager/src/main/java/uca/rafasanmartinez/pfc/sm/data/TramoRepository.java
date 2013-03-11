package uca.rafasanmartinez.pfc.sm.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import uca.rafasanmartinez.pfc.sm.model.Tramo;

/**
 * 
 * @author Rafael Sánchez Martínez, 2013
 * 
 * Se encarga de realizar consultas relativas a los tramos a la capa de persistencia.
 *
 */
@ApplicationScoped
public class TramoRepository {
	
    @Inject
    private EntityManager em;
    
    /**
     * Crea y ejecuta una query para obtener todos los tramos registrados en la aplicación.
     * @return Una lista con todos los tramos
     */
    public List<Tramo> listaTodosOrdenadosPorNombreYSentido() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tramo> criteria = cb.createQuery(Tramo.class);
        Root<Tramo> fromTramo = criteria.from(Tramo.class);
        criteria.select(fromTramo).orderBy(cb.asc(fromTramo.get("nombre")),cb.asc(fromTramo.get("sentido")));
        return em.createQuery(criteria).getResultList();
    }

}
