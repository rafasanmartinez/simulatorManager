package uca.rafasanmartinez.pfc.sm.util;

import java.util.Iterator;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;

public class SimulatorExceptionHandler extends ExceptionHandlerWrapper {
	
	private static final Logger LOG = Logger.getLogger("SimulatorHandler");
	
	private final javax.faces.context.ExceptionHandler wrapped;
	
	public SimulatorExceptionHandler(ExceptionHandler wrapped) {
		super();
		this.wrapped = wrapped;
		System.out.println("Creando wrapper");
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		System.out.println("Metiendo mano a las excepciones");
		 for (final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext();) {
			   Throwable t = it.next().getContext().getException();
			   
			   if(t.getClass().getName().equals("org.jboss.weld.context.NonexistentConversationException"))
			   {
				    LOG.info("Te pillé");
				    final FacesContext facesContext = FacesContext.getCurrentInstance();
				    ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) facesContext.getApplication().getNavigationHandler();
				    //final ExternalContext externalContext = facesContext.getExternalContext();

				    nav.performNavigation("datosCaducados");
				    //final FacesContext ctx = FacesContext.getCurrentInstance();
				   
				    while (t.getCause() != null) {
					    t = t.getCause();
					    System.out.println("Mensaje de error: " + t.getMessage() + " Class: " + t.getClass().getName());
					   }  
			   }
			   else
				   getWrapped().handle();
			 
		}
		 
	}

	
	
}
