import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Point;

public class rompecabezas {
	private static int n = 0;
	private static int m = 0;
	private static int numeroBolas = 0;
	private static Tablero  tablero = null;

	@SuppressWarnings("resource")
	public static void main(String[] arg) {

		Scanner scan = new Scanner(System.in);
		try{
			n = scan.nextInt();
			m = scan.nextInt();
			
			
			numeroBolas = scan.nextInt();
			
			if(n <= 0 || m <= 0 || numeroBolas <= 0) {
				throw new Exception("Entrada errónea.");	
			}
			tablero = new Tablero(n,m,numeroBolas);
	
			tablero.leerCoordenadasPosiciones(scan, true);
			tablero.leerCoordenadasPosiciones(scan, false);
			tablero.leerCoordenadasMuros(scan);
			
			if(tablero.comprobarTablero()) {
				throw new Exception("[]");	
			}
			tablero.imprimir();
			tablero.buscarSolucion();	
			tablero.imprimirResultado();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	
	}

}