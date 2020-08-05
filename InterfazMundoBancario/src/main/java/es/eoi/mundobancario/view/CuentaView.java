package es.eoi.mundobancario.view;

import java.util.Scanner;

import es.eoi.mundobancario.controller.CuentaViewController;
import es.eoi.mundobancario.dto.CuentaClienteDto;

public class CuentaView {
	
	public static void imprimirMenuCuentas() {
		System.out.println("Introduce el ID del cliente: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Integer id = scanner.nextInt();
		CuentaClienteDto[] cuentas = CuentaViewController.findCuentasCliente(id);
		if (cuentas.length > 0) {
			System.out.println("El cliente con ID ".concat(id.toString()).
					concat(" dispone de ").concat(String.valueOf(cuentas.length)).
					concat(" cuentas que se muestran a continuación:"));
			for (int i = 0; i < cuentas.length; i++) {
				StringBuilder cuentaToString = new StringBuilder();
				cuentaToString.append("\n");
				cuentaToString.append("Número de cuenta: ".concat(cuentas[i].getNumCuenta().toString()));
				cuentaToString.append("\n");
				cuentaToString.append("Alias de la cuenta: ".concat(cuentas[i].getAlias()));
				cuentaToString.append("\n");
				cuentaToString.append("Saldo de la cuenta: ".concat(cuentas[i].getSaldo().toString()));
				System.out.println(cuentaToString.toString());
			}
			updateAlias();
		} else {
			System.out.println("El cliente con ID ".concat(id.toString()).
					concat(" no dispone de ninguna cuenta con EOI BANK"));
		}
	}
	
	private static void updateAlias() {
		System.out.println("¿Desea modificar el alias de una de las cuentas? (y/n)");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		if (scanner.next().equals("y")) {
			System.out.println("Indique número de cuenta: ");
			scanner = new Scanner(System.in);
			Integer id = scanner.nextInt();
			System.out.println("Indique el nuevo alias: ");
			scanner = new Scanner(System.in);
			String alias = scanner.nextLine();
			CuentaViewController.updateAlias(id, alias);
			System.out.println("Alias modificado.");
		} 
	}
}
