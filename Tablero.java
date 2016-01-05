import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Point;

public class Tablero {
	private Celda[][] tablero;
	private int cols = 0;
	private int filas = 0;
	private int numeroBolas = 0;
	private ArrayList<Integer> resultado = new ArrayList<Integer>();
	private int patrones = 0;

	private int deep = 0;

	public Tablero(int cols, int filas, int nB) {
		this.cols = cols;
		this.filas = filas;
		this.numeroBolas = nB;
		inicializar();
	}

	public Tablero(Tablero t) {
		this.cols = t.cols;
		this.filas = t.filas;
		this.numeroBolas = t.numeroBolas;
		inicializar();
		for(int i = 0; i<cols; i++) {
			for(int j = 0; j<filas; j++) {
				tablero[i][j].setBola(t.tablero[i][j].getBola());
				tablero[i][j].setObjetivo(t.tablero[i][j].isObjetivo());
			}
		}
	}

	private Celda[][] duplicarCeldas(Celda[][] t) {
		Celda[][] result = new Celda[cols][filas];
		for(int i = 0; i<cols; i++) {
			for(int j = 0; j<filas; j++) {
				result[i][j] = new Celda(t[i][j]);
			}
		}
		return result;
	}

	private void inicializar() {
		tablero = new Celda[cols][filas];
		for(int i = 0; i<cols; i++) {
			for(int j = 0; j<filas; j++) {
				tablero[i][j] = new Celda();
			}
		}
	}

	private Celda[][] devolverCeldas() {
		return tablero;
	}

	public void imprimir() {
		String s = "";

		for(int i = 0; i<cols; i++) {
			for(int j = 0; j<filas; j++) {
				s = tablero[i][j].getBola() == null ? "-" : "" + tablero[i][j].getBola();
				s = tablero[i][j].isObjetivo() ? "[" + s + "]" : " " + s + " ";
				System.out.print(" " + s);
			}
			System.out.println();
		}
	}

	public void imprimir(Celda[][] t) {
		String s = "";

		for(int i = 0; i<cols; i++) {
			for(int j = 0; j<filas; j++) {
				s = t[i][j].getBola() == null ? "-" : "" + t[i][j].getBola();
				s = t[i][j].isObjetivo() ? "[" + s + "]" : " " + s + " ";
				System.out.print(" " + s);
			}
			System.out.println();
		}
	}

	public void leerCoordenadasPosiciones(Scanner scan, boolean esObjetivo) {
		for(int i = 0; i<numeroBolas; i++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			if(esObjetivo) {
				tablero[x-1][y-1] = new Celda(true,null);
			}else{
				tablero[x-1][y-1].setBola(i+1);
			}
			
		}
	}

	private Point getCoordenadasBola(Integer bola, Celda[][] t) throws Exception {
		for(int i = 0; i<cols; i++) {
			for(int j = 0; j<filas; j++) {
				if(t[i][j].getBola() != null && t[i][j].getBola().equals(bola)) {
					return new Point(i,j);
				}
			}
		}
		throw new Exception("Bola " + bola + " not found");	
	}

	private int mover(Integer bola, Celda[][] t) throws Exception{
		Point c = getCoordenadasBola(bola, t);
		int movimientos = 0;
		movimientos += moverIzquierda(c,t);
		movimientos += moverDerecha(c,t);
		movimientos += moverArriba(c,t);
		movimientos += moverAbajo(c,t);
		return movimientos;
	} 

	private void p(String m) {
		System.out.println(m);
	}

	private int moverIzquierda(Point p, Celda[][] t) {
		int counter = 0;
		int x = (int)p.getY();
		int y = (int)p.getX();

		for(int i = 0; i<x; i++) {
			if(t[y][i].getBola() != null) {
				if(i > 0 && t[y][i-1].getBola() == null ) {
					t[y][i-1].setBola(t[y][i].getBola());
					t[y][i].setBola(null);
					counter++;
				}
			}
		}
		return counter;
	}

	private int moverDerecha(Point p, Celda[][] t) {
		int counter = 0;
		int x = (int)p.getY();
		int y = (int)p.getX();

		for(int i = cols-1; i>x; i--) {
			if(t[y][i].getBola() != null) {
				if(i < cols-1 && t[y][i+1].getBola() == null ) {
					t[y][i+1].setBola(t[y][i].getBola());
					t[y][i].setBola(null);
					counter++;
				}
			}
		}
		return counter;
	}

	private int moverArriba(Point p, Celda[][] t) {
		int counter = 0;
		int x = (int)p.getY();
		int y = (int)p.getX();

		for(int i = 0; i<y; i++) {
			if(t[i][x].getBola() != null) {
				if(i > 0 && t[i-1][x].getBola() == null ) {
					t[i-1][x].setBola(t[i][x].getBola());
					t[i][x].setBola(null);
					counter++;
				}
			}
		}
		return counter;
	}

	private int moverAbajo(Point p, Celda[][] t) {
		int counter = 0;
		int x = (int)p.getY();
		int y = (int)p.getX();
		
		for(int i = filas-1; i>y; i--) {
			if(t[i][x].getBola() != null) {
				if(i < filas-1 && t[i+1][x].getBola() == null ) {
					t[i+1][x].setBola(t[i][x].getBola());
					t[i][x].setBola(null);
					counter++;
				}
			}
		}
		return counter;
	}

	private boolean comprobarSolucion(Celda[][] t) {
		for(int i = 0; i<cols; i++) {
			for(int j = 0; j<filas; j++) {
				if(t[i][j].getBola() != null && !t[i][j].isObjetivo()) {
					return false;
				}
			}
		}
		return true;
	}

	private String strRepeat(String s, int c) {
		String result = "";
		for(int i = 0; i < c; i++) {
			result += s;
		}
		return result;
	}

	private int detectarPatronMovimientos() {
		int tam = resultado.size();
		if(tam >= 4) {
			//int mit = (int)(tam/2);
			for(int i = 4; i<=tam; i += 2) {
				boolean coincidencia = true;
				for(int j = tam-1; j >= (tam - (i/2)); j--) {
					coincidencia &= resultado.get(j) == resultado.get(j-(i/2));
					if(!coincidencia) {
						break;
					}
				}
				if(coincidencia) {
					return (int)i/2;
				}
			}
		}
		
		return 0;
	}
	
	private void addResultado(int bola) {
		resultado.add(bola);
	}
	
	private void removeResultado() {
		resultado.remove(resultado.size()-1);
	}

	private boolean buscarSolucionRecursiva(Celda[][] t, int empezarEnBola) throws Exception {
		
		deep++;

		Celda[][] t2 = duplicarCeldas(t);
		for(int i = empezarEnBola; i<numeroBolas; i++) {
			while(mover(i+1,t2) > 0) {
				addResultado(i+1);
				if(comprobarSolucion(t2)) {
					deep--;
					return true;
				}
				patrones = detectarPatronMovimientos();
				if(patrones > 0) {
					patrones--;
					removeResultado();
					deep--;
					return false;
				}else {
					boolean b = buscarSolucionRecursiva(t2, 0);
					if(b) {
						deep--;
						return b;
					}
					if(patrones > 0) {
						patrones--;
						removeResultado();
						deep--;
						return false;
					}
				}
			}
		}
		removeResultado();
		deep--;
		return false;
	}

	public boolean buscarSolucion() throws Exception{
		return buscarSolucionRecursiva(tablero,0);
	}

	public void imprimirResultado() {
		System.out.println(resultado);
	}
}
