/**
 * 
 */
package uca.rafasanmartinez.pfc.sm.service;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
/**
 * @author Rafa
 * Se encarga de ejecutar la simulación, y de proveer información relativa al estado de la misma.<br>
 * Mediante "@ApplicationScoped", nos aseguramos de que sólo haya una instancia de este componente en la aplicación, a manejamos el control de concurrencia requerido mediante la utilización de las anotaciones "@Lock"
 */
public class Simulacion {
	
	/**
	 * Almacena true si el estado de la simulación es "En ejecución" o "No en ejecución" 
	 */
	private AtomicBoolean simulacionEnEjecucion = new AtomicBoolean(false);
	private AtomicBoolean pararSimulacion = new AtomicBoolean(false);
			

	/**
	 * Constructor de componente
	 */
	public Simulacion() {
		
	}

	/**
	 * Ejecuta la simulación
	 */
	public void comenzarSimulacion()
	{
		if(!comprobarYMarcarComenzada())
			return;
		
		inicializarSimulacion();
		
		int counter = 0;
		while (counter <= 100)
		{
			
			if(pararSimulacion.get())
				break;

			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			counter=+1;		
			
			
		}
		
		if(pararSimulacion.get())
		{
			abortarSimulacion();
			ejecutarAbortarSimulacion();
		}
		else
		{
			finalizarSimulacion();
		}
		
		marcarNoEnEjecucion();
		
	}
	
	@Lock(LockType.WRITE)
	private void marcarNoEnEjecucion() {
		
		simulacionEnEjecucion.set(false);
		
	}

	/** 
	 * Realiza las tareas necesarias para terminar la simulación
	 */
	private void finalizarSimulacion() {

		
		
	}
	

	@Lock(LockType.WRITE)
	/**
	 * Comprueba si la simulación ha comenzado ya, y marca la simulación como en ejecución en caso afirmativo
	 * @return Devuelve true si la simulación no había comenzado y se marca como en en ejecución. Devuelve false si la simulación ya había comenzado previamente.
	 */
	private boolean comprobarYMarcarComenzada()
	{
		if(simulacionEnEjecucion.get())
			return false;
		else
		{
			simulacionEnEjecucion.set(true);
			return false;
		}
		
	}

	/**
	 * Inicializa el estado de una nueva simulación
	 */
	private void inicializarSimulacion()
	{
		pararSimulacion.set(false); 
	}
	
	/**
	 * @return the simulacionEnEjecucion
	 */
	public boolean getSimulacionEnEjecucion() {
		return simulacionEnEjecucion.get();
	}


	/**
	 * @param simulacionEnEjecucion the simulacionEnEjecucion to set
	 */
	public void setSimulacionEnEjecucion(boolean bSimulacionEnEjecucion) {
		this.simulacionEnEjecucion.set(bSimulacionEnEjecucion);
	}
	
	/**
	 * Realiza las tareas necesarias para abortar la simulación
	 */
	private void ejecutarAbortarSimulacion()
	{
		
		
	}
	
	@Lock(LockType.WRITE)
	/**
	 * Marca en la simulación la orden de abortar
	 */
	public void abortarSimulacion()
	{
		if(simulacionEnEjecucion.get() && !pararSimulacion.get())
		pararSimulacion.set(true);
	}




}
