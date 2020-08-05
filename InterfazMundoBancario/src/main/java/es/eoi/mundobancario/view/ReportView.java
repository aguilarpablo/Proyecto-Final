package es.eoi.mundobancario.view;

import java.util.Scanner;

import es.eoi.mundobancario.controller.ReportViewController;

public class ReportView {

	public static void imprimirMenuReports() {

		System.out.println("¿Qué reporte desea generar?");
		System.out.println("1.-Reporte con información de un cliente.");
		System.out.println("2.-Reporte con información de un préstamo.");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Integer opcion = scanner.nextInt();
		switch (opcion) {
		case 1:
			imprimirReporteCliente();
			break;
		case 2:
			imprimirReportePrestamo();
			break;
		default:
			System.out.println("Opción no válida.");
			break;
		}

	}

	public static void imprimirReporteCliente() {
		System.out.println("Introduce el ID del cliente: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Integer id = scanner.nextInt();
		ReportViewController.generarPDFCliente(id);
	}

	public static void imprimirReportePrestamo() {
		System.out.println("Introduce el ID del préstamo: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		Integer id = scanner.nextInt();
		System.out.println();
		ReportViewController.generarPDFPrestamo(id);
	}

}
