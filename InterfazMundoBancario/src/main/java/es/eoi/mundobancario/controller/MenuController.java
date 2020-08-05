package es.eoi.mundobancario.controller;

import es.eoi.mundobancario.InterfazMundoBancarioApplication;
import es.eoi.mundobancario.view.ClienteView;
import es.eoi.mundobancario.view.CuentaView;
import es.eoi.mundobancario.view.EjecutarAmortizacionView;
import es.eoi.mundobancario.view.PrestamoView;
import es.eoi.mundobancario.view.ReportView;

public class MenuController {
	
	public static void gestionarOpciones(int opcion) {

		switch (opcion) {
		case 1:
			ClienteView.imprimirMenuModificarUsuario();
			InterfazMundoBancarioApplication.start();
			break;

		case 2:
			CuentaView.imprimirMenuCuentas();
			InterfazMundoBancarioApplication.start();
			break;
		case 3:
			PrestamoView.imprimirMenu();
			InterfazMundoBancarioApplication.start();
			break;
		case 4:
			EjecutarAmortizacionView.imprimirMenuEjecutarAmortizacion();
			InterfazMundoBancarioApplication.start();
			break;
		case 5:
			ReportView.imprimirMenuReports();
			InterfazMundoBancarioApplication.start();
			break;
		case 6:
			System.out.println("¡Hasta luego, vuelva ponto!");
			break;
		default:
			System.out.println("Opción no válida.");
			InterfazMundoBancarioApplication.start();
			break;
		}

	}

}
