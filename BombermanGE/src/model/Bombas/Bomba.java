package model.Bombas;

import java.util.Timer;
import java.util.TimerTask;

import model.Casillas.Casilla;
import model.Casillas.CasillaTablero;
import model.Casillas.Explosion;
import model.Tableros.Tablero;

public abstract class Bomba implements Casilla{
	private int i;
	private int j;
	private boolean explotada;
	private boolean activa;
	private int tiempoExplosion;
	private Timer timer = null;
	
	public Bomba(int pI, int pJ) {
		this.i = pI;
		this.j = pJ;
		this.explotada = false;
		this.activa = false;
		this.tiempoExplosion = 3000;
		activar();
	}

	
	
	//METODOS GET O SIMILAR
	public boolean haExplotado() {
		return explotada;
	}
	
	
	
	//LOGICA EXPLOSION BOMBA
	public void activar() {
		if (!activa) {
			activa=true;
			TimerTask timerTask = new TimerTask(){
				@Override
				public void run() {
					explotar();
				}
			};
			timer = new Timer();
			timer.schedule(timerTask, tiempoExplosion);
		}
	}
	
	public void explotar(){
		if (!explotada) 
		explotada = true;
		Tablero.getTablero().destruirBloques(i, j);
	}
	
	

	//METODOS CASILLA
	@Override
	public boolean puedeMoverse() {
		return false;
	}

	@Override
	public boolean esObjeto(String pTipo) {
		if(pTipo.equals("Bomba")) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void destruir(CasillaTablero pC, int pI, int pJ) {
		if (!explotada) {
			explotada = true;
			Tablero.getTablero().destruirBloques(i, j);
		}
		pC.cambiarCasilla(new Explosion(pI, pJ));
	}
	
	@Override
	public void vaciar(CasillaTablero pC) {
	}

	@Override
	public boolean isVivo() {
    	return false;
	}

	@Override
	public void mover(int pI, int pJ) {
	}
}
