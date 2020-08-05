package es.eoi.mundobancario.controller;

import es.eoi.mundobancario.InterfazMundoBancarioApplication;

public class EjecutarAmortizacionViewController {
	
	private static final String URL_EJECUTAR_AMORTIZACION = "/cuentas/ejecutarAmortizacionesDiarias";
	
	public static void ejecutarAmortizacion() {
		
		String urlEjecucion =  InterfazMundoBancarioApplication.URL_GATEWAY.
				concat(URL_EJECUTAR_AMORTIZACION);
		InterfazMundoBancarioApplication.restTemplate.
		postForEntity(urlEjecucion, null, String.class);
		
	}

}
