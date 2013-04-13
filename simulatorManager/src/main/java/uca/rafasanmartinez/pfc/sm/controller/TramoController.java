package uca.rafasanmartinez.pfc.sm.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import uca.rafasanmartinez.pfc.sm.model.Tramo;

/**
 * Controla las actividades de la aplicación relativas a los Tramos
 * 
 * @author Rafael Sánchez Martínez, 2013
 * 
 */
@SuppressWarnings("serial")
@ConversationScoped
@Named
@Stateful
public class TramoController implements Serializable {

	@Inject
	Logger log;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	private @Inject
	Conversation conversation;
	
	@Inject @Any
	Event<Tramo> tramoEvent;

	// @Inject
	// @Push(topic = "errorActualizando") Event<String> eventoErrorActualizando;

	@Inject
	private FacesContext facesContext;

	private Tramo tramo;

	private Tramo nuevoTramo;

	/**
	 * Devuelve el tramo en modificación
	 * 
	 * @return the tramo
	 */
	@Produces
	@Named
	public Tramo getTramo() {
		if (tramo == null)
			log.fine("Obteniendo Tramo nulo");
		else
			log.fine("Obteniendo Tramo" + tramo.getNombre());
		return tramo;
	}

	/**
	 * Devuelve el nuevo tramo a crear
	 * 
	 * @return
	 */
	@Produces
	@Named
	public Tramo getNuevoTramo() {
		if (nuevoTramo == null)
			log.fine("Obteniendo Nuevo Tramo nulo");
		else
			log.fine("Obteniendo Nuevo Tramo (" + nuevoTramo.getNombre() + ")");
		return nuevoTramo;
	}

	/**
	 * Guarda el tramo a crear
	 * 
	 * @param tramo
	 * 
	 */
	public void setNuevoTramo(Tramo tramo) {
		this.nuevoTramo = tramo;
	}

	public String comenzarNuevo() {
		
		if (conversation.isTransient())
			conversation.begin();

		return "nuevoTramo";
	}

	public String registrarNuevo() throws Exception {
		try {
			em.persist(nuevoTramo);
			tramoEvent.select(new AnnotationLiteral<Registered>(){}).fire(nuevoTramo);
		} catch (Exception e) {
			procesarExcepcionActualizacion(e);
			return null;
		}
		conversation.end();
		return "volver";
	}

	public String comenzarModificacion(Tramo tramo) {
		log.fine("Poniendo Tramo" + tramo.getNombre());
		this.tramo = tramo;
		if (conversation.isTransient())
			conversation.begin();
		log.fine("Iniciando conversación " + conversation.getId());
		return "modificar";
	}
	
	public String borrarTramo(Tramo tramo) {
		log.fine("Borrando Tramo" + tramo.getNombre());
		Tramo dTramo = em.find(Tramo.class, tramo.getId());
		em.remove(dTramo);
		tramoEvent.select(new AnnotationLiteral<Deleted>(){}).fire(tramo);
		return "borrado";
	}

	/**
	 * El tramo a modificar
	 * 
	 * @param tramo
	 * 
	 */
	public void setTramo(Tramo tramo) {
		log.fine("Poniendo Tramo" + tramo.getNombre());
		this.tramo = tramo;
	}

	/**
	 * Registra los cambios el la capa de persistencia
	 * 
	 * @return
	 */
	public String registrarCambios() {
		log.fine("Registrando Cambios en tramo" + tramo.getNombre());
		try {
			em.merge(tramo);
			em.flush();
			tramoEvent.select(new AnnotationLiteral<Updated>(){}).fire(tramo);
		} catch (Exception e) {
			procesarExcepcionActualizacion(e);
			return null;
		}
		conversation.end();
		log.fine("Conversacion terminada");
		return "volver";
	}

	/**
	 * Termina la conversación sin registrar los cambios
	 * 
	 * @return
	 */
	public String cancelarConversacion() {
		log.fine("Entrando en cancelar conversación");
		conversation.end();
		log.fine("Conversacion terminada por cancelacion.");
		return "volver";
	}

	@PostConstruct
	public void inicializacion() {
		log.fine("Creando controlador de Tramos");
		log.fine("Inicializando tramo nuevo");
		log.fine("Creando nuevo tramo");
		this.nuevoTramo = new Tramo();
	}

	@PreDestroy
	public void destruccion() {
		log.fine("Destruyendo el controlador.");
	}

	public String getConversationInfo() {
		return "Conversation:" + conversation.getId() + " Is Transient: "
				+ conversation.isTransient();
	}

	public void setConversationInfo(String info) {

	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}
	
	private void procesarExcepcionActualizacion(Exception e)
	{
		String rootMessage = getRootErrorMessage(e);

		if (rootMessage.startsWith("Duplicate entry")) {
			// eventoErrorActualizando.fire("Entrada Duplicada. No pueden existir tramos con el mismo nombre y sentido");
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Tramo duplicado: ",
					"No pueden existir tramos con el mismo nombre y sentido");
			facesContext.addMessage(null, m);
		} else {
			// eventoErrorActualizando.fire("Problema inesperado al salvar nueva entrada: "
			// + rootMessage);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Problema inesperado al salvar nuevo tramo: ",
					rootMessage);
			facesContext.addMessage(null, m);
		}
	}

}
