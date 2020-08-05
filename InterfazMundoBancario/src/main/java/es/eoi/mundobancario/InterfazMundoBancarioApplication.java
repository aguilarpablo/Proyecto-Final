package es.eoi.mundobancario;

import org.springframework.web.client.RestTemplate;

import es.eoi.mundobancario.view.MenuPrincipalView;

public class InterfazMundoBancarioApplication {
	
	public static final String URL_GATEWAY = "http://localhost:8080";
	public static final RestTemplate restTemplate = new RestTemplate();
	
	public static void main(String[] args) {
		
		System.out.println("***********************************************");
		System.out.println("Bienvenido a tu Aplicación de Banca Flipante!!!");
		System.out.println("***********************************************");

		start();

	}

	public static void start() {

		MenuPrincipalView.imprimirMenu();

	}

	

}
