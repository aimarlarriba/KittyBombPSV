package model.Mobs;

import java.util.Timer;
import java.util.TimerTask;

import model.Bloques.BloqueFactory;
import model.Casillas.Casilla;
import model.Casillas.CasillaTablero;
import model.Casillas.Explosion;
import model.Tableros.Tablero;

public class Enemigo implements Casilla{
    private int i, j;
    private boolean vivo;
    private boolean esperaFinalizada=false;
    private Timer timer = null;
    private int tiempoMovimiento;
    
    public Enemigo(int pI, int pJ) {
        i = pI;
        j = pJ;
        vivo = true;
        tiempoMovimiento = 2000;
        empezarTimer();
    }



	//METODOS GET O SIMILAR
    public boolean isVivo() {
        return vivo;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    
    
    //LOGICA MOVIMIENTO ENEMIGO
    public void empezarTimer() {
        if (timer == null) {
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                	if(esperaFinalizada) {
                		if (vivo) {
                            Tablero.getTablero().moverEnemigo(i, j);
                        } else {
                            timer.cancel(); 
                        }
                	} else {
                		esperaFinalizada=true;
                	}
                    
                }
            };
            timer.scheduleAtFixedRate(timerTask, 0, tiempoMovimiento);
        }
    }

    
    
	//METODOS CASILLA
	@Override
	public boolean puedeMoverse() {
		return false;
	}

	@Override
	public boolean esObjeto(String pTipo) {
		if(pTipo.equals("Enemigo")) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void vaciar(CasillaTablero pC) {
	}
	
	@Override
	public void mover(int pI, int pJ) {
		if (i!=pI || j!=pJ) {
    		CasillaTablero c=Tablero.getTablero().getCasilla(pI, pJ);
    		c.cambiarCasilla(this);
    		Tablero.getTablero().getCasilla(i, j).cambiarCasilla(BloqueFactory.getBloqueFactory().generate("Vacio"));
    	}
    	i = pI;
    	j = pJ;
	}

	@Override
	public void destruir(CasillaTablero pC, int pI, int pJ) {
		vivo = false;
        if (timer != null) {
            timer.cancel();
        }
        pC.cambiarCasilla(new Explosion(pI, pJ));
	}
}
