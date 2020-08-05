package es.eoi.mundobancario.view;

import java.util.Scanner;

import es.eoi.mundobancario.controller.MenuController;

public class MenuPrincipalView {

	public static void imprimirMenu() {

		System.out.println("\n");
		System.out.println("¿Qué operación quieres realizar?");
		System.out.println("\n");
		System.out.println("1.-Modificar información de usuario.");
		System.out.println("2.-Consultar mis cuentas.");
		System.out.println("3.-Gestionar/Solicitar prestamo.");
		System.out.println("4.-Amortizar prestamo.");
		System.out.println("5.-Generación de reportes.");
		System.out.println("6.-SALIR.");

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		MenuController.gestionarOpciones(scanner.nextInt());
	}

}
