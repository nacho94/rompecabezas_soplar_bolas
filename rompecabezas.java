import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Point;

public class rompecabezas {
	private static int n = 0;
	private static int m = 0;
	private static int numeroBolas = 0;
	private static Tablero  tablero = null;

	public static void main(String[] arg) {

		Scanner scan = new Scanner(System.in);

		n = scan.nextInt();
		m = scan.nextInt();
		
		
		numeroBolas = scan.nextInt();

		tablero = new Tablero(n,m,numeroBolas);

		tablero.leerCoordenadasPosiciones(scan, true);
		tablero.leerCoordenadasPosiciones(scan, false);
		tablero.imprimir();

		try{
			tablero.buscarSolucion();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		tablero.imprimirResultado();
	}

}