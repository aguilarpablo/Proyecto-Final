package es.eoi.mundobancario.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import es.eoi.mundobancario.controller.EjecutarAmortizacionViewController;

public class EjecutarAmortizacionView {
	
	public static void imprimirMenuEjecutarAmortizacion() {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("Se van a ejecutar todas las amortizaciones que se deben pagar el día "
				.concat(formatter.format(Calendar.getInstance().getTime())));
		System.out.println("¿Está de acuerdo? (y/n)");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		if (scanner.next().equals("y")) {
			EjecutarAmortizacionViewController.ejecutarAmortizacion();
			System.out.println("Amortizaciones ejecutadas");
		} else {
			System.out.println("Operación cancelada.");
		}
	}

}
