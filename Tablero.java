import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Point;

public class Tablero {
	private Celda[][] tablero;
	private int cols = 0;
	private int filas = 0;
	private int numeroBolas = 0;
	private ArrayList<ArrayList<Integer> > resultadoTotal = new ArrayList<ArrayList<Integer> >();
	private ArrayList<Integer> resultado = new ArrayList<Integer>();
	private int patrones = 0;
	boolean solucion = false;

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
				tablero[i][j].setMuroEste(t.tablero[i][j].isMuroEste());
				tablero[i][j].setMuroNorte(t.tablero[i][j].isMuroNorte());
				tablero[i][j].setMuroOeste(t.tablero[i][j].isMuroOeste());
				tablero[i][j].setMuroSur(t.tablero[i][j].isMuroSur());
			}
		}
	}
	public void limpiar() {
		inicializar();
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
	
	private String caracterMuro(int x, int y) {
		Celda c = tablero[x][y];
		if(c.isMuroNorte()) {
			return "↑";
		}else if(c.isMuroSur()){
			return "↓";
		}else if(c.isMuroEste()){
			return "→";
		}else if(c.isMuroOeste()){
			return "←";
		}
		
		return " ";
	}
	
	public void imprimir() {
		String s = "";

		for(int i = 0; i<cols; i++) {
			for(int j = 0; j<filas; j++) {
				s = tablero[i][j].getBola() == null ? " -" : " " + tablero[i][j].getBola();
				s += caracterMuro(i, j);
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

	public void leerCoordenadasPosiciones(Scanner scan, boolean esObjetivo) throws Exception  {
		try{
			for(int i = 0; i<numeroBolas; i++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			if(x <= 0 || y <= 0) {

				throw new Exception();
			}
			if(esObjetivo) {
				tablero[x-1][y-1] = new Celda(true,null);
			}else{
				tablero[x-1][y-1].setBola(i+1);
			}
			}
		} catch (Exception e) {
			throw new Exception("Entrada errónea.");
		}

	}
	
	public void leerCoordenadasMuros(Scanner scan) {
		scan.nextLine();
		String palabra = scan.nextLine();
		System.out.println("palabra: " + palabra);
		Scanner scan1 = new Scanner(palabra);
		
		while(scan1.hasNext()) {
			int x = scan1.nextInt();
			 
			int y = scan1.nextInt();
			
			String letra = scan1.next();
			System.out.println("numero1: " + x + " numero2: " + y + " letra: " + letra);
			
			if(letra.equals("N")) {
				tablero[x-1][y-1].setMuroNorte(true);
			}else if(letra.equals("S")){
				tablero[x-1][y-1].setMuroSur(true);
			}else if(letra.equals("O")){
				tablero[x-1][y-1].setMuroOeste(true);
			}else if(letra.equals("E")){
				tablero[x-1][y-1].setMuroEste(true);
			}
		}
		scan1.close();
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

	private int mover(Integer bola, Celda[][] t) throws Exception {
		Point c = getCoordenadasBola(bola, t);
		int movimientos = 0;
		movimientos += moverIzquierda(c,t);
		movimientos += moverDerecha(c,t);
		movimientos += moverArriba(c,t);
		movimientos += moverAbajo(c,t);
		return movimientos;
	}

	private boolean puedoMover(Integer bola, Celda[][] t) throws Exception {
		Celda[][] aux = duplicarCeldas(t);
		return mover(bola, aux) > 0;
	}

	private void p(String m) {
		System.out.println(m);
	}

	private int moverIzquierda(Point p, Celda[][] t) {
		int counter = 0;
		int x = (int)p.getY();
		int y = (int)p.getX();

		for(int i = 0; i<x; i++) {
			if(t[y][i].getBola() != null && !t[y][i].isMuroOeste()) {
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
			if(t[y][i].getBola() != null && !t[y][i].isMuroEste()) {
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
			if(t[i][x].getBola() != null && !t[i][x].isMuroNorte()) {
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
			if(t[i][x].getBola() != null && !t[i][x].isMuroSur()) {
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
		resultadoTotal.add(new ArrayList<Integer>(resultado));
		solucion = true;
		return true;
	}

	private String strRepeat(String s, int c) {
		String result = "";
		for(int i = 0; i < c; i++) {
			result += s;
		}
		return result;
	}

	private boolean detectarPatronMovimientos() {
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
					patrones = (int)i/2;
					return true;
				}
			}
		}
		patrones = 0;
		return false;
	}

	private void eliminarElementoPatron() {
		patrones--;
		removeResultado();
	}
	
	private void addResultado(int bola) {
		resultado.add(bola);
		// System.out.println(resultado);
	}
	
	private void removeResultado() {
		resultado.remove(resultado.size()-1);
		// System.out.println(resultado);
	}

	private boolean buscarSolucionRecursiva(Celda[][] t) throws Exception {

		for(int bola = 1; bola <= numeroBolas; bola++) {
			Celda[][] t2 = duplicarCeldas(t);
			if (puedoMover(bola, t2)) {
				mover(bola, t2);
				addResultado(bola);
				comprobarSolucion(t2);
				if (detectarPatronMovimientos()) {
					eliminarElementoPatron();
					return false;
				}
				boolean result = buscarSolucionRecursiva(t2);
				if (result) {
					return true;
				}
				if (patrones > 0) {
					eliminarElementoPatron();
					return false;
				}
				removeResultado();
			}
		}

		return false;
	}

	public boolean buscarSolucion() throws Exception{
		boolean b = buscarSolucionRecursiva(tablero);
		if(solucion == false) {
			throw new Exception("Rompecabezas sin solución.");	
		}
		return b;
	}

	public void imprimirResultado() {
		prueba();
		for(int i = 0; i<resultadoTotal.size(); i++) {
			ArrayList<Integer> q = new ArrayList<Integer>();
			q.add(-1);
			if(!resultadoTotal.get(i).equals(q)){
				System.out.println(resultadoTotal.get(i));
			}
		}
	}
	public boolean comprobarTablero() {
		return comprobarSolucion(tablero);
	}
	
	public void prueba() {
		 String inicial = "";
		 for(int k = 0; k<resultadoTotal.size(); k++) {
			 ArrayList<Integer> n = resultadoTotal.get(k);
			 inicial = "";
			for(int i = 0; i<n.size(); i++) {
				inicial += n.get(i) + ""; 
			}
			for(int i = k+1; i<resultadoTotal.size(); i++) {
				String p = "";
				ArrayList<Integer> n2 = resultadoTotal.get(i);
					for(int j = 0; j<n2.size(); j++) {
						p += n2.get(j);
					}
					if(p.contains(inicial)) {
						ArrayList<Integer> q = new ArrayList<Integer>();
						q.add(-1);
						resultadoTotal.set(i,q);
					}
			}
		 }

	 }
}
