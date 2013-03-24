package uca.rafasanmartinez.pfc.sm.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class SimulatorExceptionHandlerFactory extends ExceptionHandlerFactory {

	private final ExceptionHandlerFactory parent;
	
	public SimulatorExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		super();
		//System.out.println("Creando factoría");
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		//System.out.println("Obteniendo Exception Handler");
		return new SimulatorExceptionHandler(this.parent.getExceptionHandler());
	}

	
	
}
