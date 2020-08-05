package es.eoi.mundobancario.view;

import java.util.Scanner;

import es.eoi.mundobancario.controller.ClienteViewController;
import es.eoi.mundobancario.dto.ClienteDto;

public class ClienteView {
	
	public static void imprimirMenuModificarUsuario() {
		System.out.println("Con esta operación únicamente puede cambiarse el email.");
		System.out.println("Indica el ID del usuario que desea modificar: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Integer id = scanner.nextInt();
		ClienteDto cliente = ClienteViewController.buscarCliente(id);
		System.out.println("El cliente con ID: ".concat(id.toString()).concat(" tiene los siguientes datos: "));
		System.out.println("Nombre: ".concat(cliente.getNombre()));
		System.out.println("Usuario: ".concat(cliente.getUsuario()));
		System.out.println("Email: ".concat(cliente.getEmail()));
		System.out.println("¿Desea modificar el email? (y/n)");
		if (scanner.next().equals("y")) {
			System.out.println("Introduzca el nuevo email: ");
			scanner = new Scanner(System.in);
			String email = scanner.next();	
			ClienteViewController.updateEmail(id, email);
			System.out.println("Email modificado.");
		} else {
			System.out.println("Modificación cancelada.");
		}
		
	}

}
