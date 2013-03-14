package uca.rafasanmartinez.pfc.sm.util;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PreRender {

	@Inject Logger log;
	
	public PreRender() {
		// TODO Auto-generated constructor stub
	}
	
	public void log(String page ) {
		log.info( "Page " +  page + " about to be rendered");
	}

}
