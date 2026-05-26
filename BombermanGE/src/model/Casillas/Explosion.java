package model.Casillas;

import java.util.Timer;
import java.util.TimerTask;

import model.Bloques.BloqueFactory;
import model.Tableros.Tablero;

public class Explosion implements Casilla{
	private int i;
	private int j;
	boolean activa;
	private int tiempoExplosion;
	private Timer timer = null;
	
	public Explosion(int pI, int pJ) {
		this.i = pI;
		this.j = pJ;
		this.activa=false;
		this.tiempoExplosion = 2000;
		activar();
	}
	
	
	
	//LOGICA APAGADO EXPLOSION
	public void activar() {
	    if (!activa) {
	        activa = true;
	        if (timer != null) {
	            timer.cancel();
	        }
	        timer = new Timer();
	        TimerTask timerTask = new TimerTask() {
	            @Override
	            public void run() {
	                Tablero.getTablero().apagar(i, j);
	            }
	        };
	        timer.schedule(timerTask, tiempoExplosion);
	    }
	    return;
	}

	
	
	//METODOS CASILLA
	@Override
	public boolean puedeMoverse() {
		return true;
	}

	@Override
	public boolean esObjeto(String pTipo) {
		if(pTipo.equals("Explosion")) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean isVivo() {
    	return false;
    }
	
	@Override
	public void destruir(CasillaTablero pC, int pI, int pJ) {
		if (timer != null) {
            timer.cancel();
        }
		pC.cambiarCasilla(new Explosion(pI, pJ));
	}
	
	@Override
	public void vaciar(CasillaTablero pC) {
		pC.cambiarCasilla(BloqueFactory.getBloqueFactory().generate("Vacio"));
	}
	
	@Override
	public void mover(int pI, int pJ) {
	}
}
