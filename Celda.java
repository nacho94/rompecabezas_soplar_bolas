import java.util.Scanner;
import java.util.ArrayList;
import java.awt.Point;

public class Celda {
	private boolean objetivo = false;
	private Integer bola = null;
	
	private boolean muroEste = false;
	private boolean muroOeste = false;
	private boolean muroNorte = false;
	private boolean muroSur = false;
	
	public Celda(boolean objetivo, Integer bola) {
		this.objetivo = objetivo;
		this.bola = bola;
	}

	public Celda() {

	}

	public Celda(Celda c) {
		this.objetivo = c.isObjetivo();
		this.bola = c.getBola();
		this.muroEste = c.muroEste;
		this.muroNorte = c.muroNorte;
		this.muroOeste = c.muroOeste;
		this.muroSur = c.muroSur;
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

	public boolean isMuroEste() {
		return muroEste;
	}

	public void setMuroEste(boolean muroEste) {
		this.muroEste = muroEste;
	}

	public boolean isMuroOeste() {
		return muroOeste;
	}

	public void setMuroOeste(boolean muroOeste) {
		this.muroOeste = muroOeste;
	}

	public boolean isMuroNorte() {
		return muroNorte;
	}

	public void setMuroNorte(boolean muroNorte) {
		this.muroNorte = muroNorte;
	}

	public boolean isMuroSur() {
		return muroSur;
	}

	public void setMuroSur(boolean muroSur) {
		this.muroSur = muroSur;
	}
}

