package model.Casillas;

import java.util.Timer;
import java.util.TimerTask;

import model.Mobs.Enemigo;
import model.Tableros.Tablero;

public class Invocacion implements Casilla {
	private int i;
	private int j;
	private int tiempoInvocacion=5000;
	private Timer timer=null;

	public Invocacion(int pI, int pJ) {
		i=pI;
		j=pJ;
		activar();
	}
	
	
	
	public void activar() {
		TimerTask timerTask = new TimerTask(){
			@Override
			public void run() {
				Tablero.getTablero().getCasilla(i, j).cambiarCasilla(new Enemigo(i,j));
			}
		};
		timer = new Timer();
		timer.schedule(timerTask, tiempoInvocacion);
	}
	
	
	
	@Override
	public boolean puedeMoverse() {
		return false;
	}

	@Override
	public boolean esObjeto(String pTipo) {
		if(pTipo.equals("Invocacion")) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void destruir(CasillaTablero pC, int pI, int pJ) {
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
