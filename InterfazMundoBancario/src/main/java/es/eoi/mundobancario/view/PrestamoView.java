package es.eoi.mundobancario.view;

import java.util.Scanner;

import es.eoi.mundobancario.controller.PrestamoViewController;
import es.eoi.mundobancario.dto.CreatePrestamoDto;
import es.eoi.mundobancario.dto.PrestamoDto;

public class PrestamoView {
	
	public static void imprimirMenu() {
		
		System.out.println("Indique el número de la cuenta con la que desea solicitar un préstamo: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Integer numCuenta = scanner.nextInt();
		
		PrestamoDto[] prestamosVivos = PrestamoViewController.findPrestamosVivosByNumCuenta(numCuenta);
		
		if (prestamosVivos.length > 0) {
			System.out.println("No puede solicitar un préstamo con este número de cuenta porque ya existe un préstamo asociado a ésta.");
		} else {
			CreatePrestamoDto dto = new CreatePrestamoDto();
			System.out.println("Especifique la descripción del préstamo: ");
			scanner = new Scanner(System.in);
			String descripcion = scanner.nextLine();
			dto.setDescripcion(descripcion);
			System.out.println("Importe del préstamo: ");
			scanner = new Scanner(System.in);
			Double importe = scanner.nextDouble();
			dto.setImporte(importe);
			System.out.println("Plazos del préstamo: ");
			scanner = new Scanner(System.in);
			Integer plazos = scanner.nextInt();
			dto.setPlazos(plazos);
			PrestamoViewController.createPrestamo(dto, numCuenta);
			System.out.println("Préstamo solicitado.");
		}
		
	}

}
