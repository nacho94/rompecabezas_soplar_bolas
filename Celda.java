import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Point;

public class Celda {
	private boolean objetivo = false;
	private Integer bola = null;
	public Celda(boolean objetivo, Integer bola) {
		this.objetivo = objetivo;
		this.bola = bola;
	}

	public Celda() {

	}

	public Celda(Celda c) {
		this.objetivo = c.isObjetivo();
		this.bola = c.getBola();
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

	public void setObjetivo(boolean b) {
		this.objetivo = b;
	}
}

