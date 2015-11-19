import java.util.Scanner;
class celda {
	private boolean objetivo = false;
	private Integer bola = null;
	public celda(boolean objetivo, Integer bola) {
		this.objetivo = objetivo;
		this.bola = bola;
	}

	public celda() {

	}

	public void setBola(Integer bola) {
		this.bola = bola;
	}

	public Integer getBola() {
		return this.bola;
	}

	public boolean isObjetivo() {
		return this.objetivo;
	}
}

public class rompecabezas {
	private static int n = 0;
	private static int m = 0;
	private static int numeroBolas = 0;
	private static celda[][] tablero;
	public static void main(String[] arg) {

		Scanner scan = new Scanner(System.in);

		n = scan.nextInt();
		m = scan.nextInt();

		inicializarTablero(n,m);
		
		numeroBolas = scan.nextInt();
		leerCoordenadasPosiciones(scan,true);
		leerCoordenadasPosiciones(scan,false);
		imprimirTablero();

		
	}

	private static void inicializarTablero(int n, int m) {
		tablero = new celda[n][m];
		for(int i = 0; i<n; i++) {
			for(int j = 0; j<m; j++) {
				tablero[i][j] = new celda();
			}
		}
	}

	public static void leerCoordenadasPosiciones(Scanner scan, boolean esObjetivo) {
		for(int i = 0; i<numeroBolas; i++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			if(esObjetivo) {
				tablero[x-1][y-1] = new celda(true,null);
			}else{
				tablero[x-1][y-1].setBola(i+1);
			}
			
		}
	}

	private static void imprimirTablero() {
		String s = "";

		for(int i = 0; i<n; i++) {
			for(int j = 0; j<m; j++) {
				s = tablero[i][j].getBola() == null ? "-" : "" + tablero[i][j].getBola();
				s = tablero[i][j].isObjetivo() ? "[" + s + "]" : " " + s + " ";
				System.out.print(" " + s);
			}
			System.out.println();
		}
	}
}