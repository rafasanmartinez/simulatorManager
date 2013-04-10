package uca.rafasanmartinez.pfc.sm.data;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import uca.rafasanmartinez.pfc.sm.model.Tramo;

@RequestScoped
public class TramoListProducer {
	
	@Inject
	Logger log;

	@Inject
	TramoRepository tramoRepository;

	private List<Tramo> tramos;

	@Produces
	@Named
	public List<Tramo> getTramos() {
		return tramos;
	}

	@PostConstruct
	public void obtenerTodosLotTramosPorNombreYSentido() {
		
		tramos = tramoRepository.listaTodosOrdenadosPorNombreYSentido();
	}
	
    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Tramo member) {
        log.info("Escuchada modificacion en tramos");
    	obtenerTodosLotTramosPorNombreYSentido();
    }

}
