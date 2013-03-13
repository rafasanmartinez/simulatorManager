package uca.rafasanmartinez.pfc.sm.controller;

import java.io.Serializable;

import javax.enterprise.inject.Model;

@SuppressWarnings("serial")
@Model
public class PageController implements Serializable {
	
	
	public String goHome()
	{
		return "home";
	}
	
	public String goConfiguracion()
	{
		return "configuracion";
	}

	public String goTramosCrud()
	{
		return "tramosCrud";
	}
}
