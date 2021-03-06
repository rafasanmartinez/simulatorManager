package uca.rafasanmartinez.pfc.sm.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import uca.rafasanmartinez.pfc.sm.model.Tramo;

/**
 * Controla las actividades de la aplicación relativas a los Tramos
 * 
 * @author Rafael Sánchez Martínez, 2013
 * 
 */
@SuppressWarnings("serial")
@ConversationScoped @Named
public class TramoController implements Serializable {

	@Inject
	Logger log;

	private @Inject Conversation conversation;
	private Tramo tramo;

	/**
	 * @return the tramo
	 */
	@Produces
	@Named
	public Tramo getTramo() {
		if (tramo == null)
			log.info("Obteniendo Tramo nulo");
		else
			log.info("Obteniendo Tramo" + tramo.getNombre());
		return tramo;
	}
	
	public void comenzarModificacion(Tramo tramo)
	{
		log.info("Poniendo Tramo" + tramo.getNombre());
		this.tramo = tramo;
		conversation.begin();
		log.info("Iniciando conversación " + conversation.getId());
	}
	
	public void iniciarConversación()
	{
		
	}

	/**
	 * @param tramo
	 *            the tramo to set
	 */
	public void setTramo(Tramo tramo) {
		log.info("Poniendo Tramo" + tramo.getNombre());
		this.tramo = tramo;
	}

	public void registrarCambios() {
		log.info("Registrando Cambios en tramo" + tramo.getNombre());
		conversation.end();
		log.info("Conversacion terminada");
	}

	@PostConstruct
	public void inicializacion() {
		log.info("Creando controlador de Tramos");
	}
	
	@PreDestroy
	public void destruccion() {
		log.info("Destruyendo el controlador.");
	}
	
	public String  getConversationInfo() {
		return "Conversation:" + conversation.getId() + " Is Transient: " + conversation.isTransient();
	}
	
	public void setConversationInfo(String info) {
		
	}
	
}
