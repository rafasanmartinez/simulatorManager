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

import uca.rafasanmartinez.pfc.sm.controller.Deleted;
import uca.rafasanmartinez.pfc.sm.controller.Registered;
import uca.rafasanmartinez.pfc.sm.controller.Updated;
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
	public void obtenerTodosLosTramosPorNombreYSentido() {

		tramos = tramoRepository.listaTodosOrdenadosPorNombreYSentido();
	}

	// Observers para actualizar la lista

	public void onTramoListDeleted(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Deleted Tramo tramo) {
		log.fine("Tramo " + tramo.getNombre() + " borrado.");
		obtenerTodosLosTramosPorNombreYSentido();
	}

	public void onTramoListRegistered(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Registered Tramo tramo) {
		log.fine("Tramo " + tramo.getNombre() + " creado.");
		obtenerTodosLosTramosPorNombreYSentido();
	}

	public void onTramoListUpdated(
			@Observes(notifyObserver = Reception.IF_EXISTS) @Updated Tramo tramo) {
		log.fine("Tramo " + tramo.getNombre() + " modificado.");
		obtenerTodosLosTramosPorNombreYSentido();
	}

}
