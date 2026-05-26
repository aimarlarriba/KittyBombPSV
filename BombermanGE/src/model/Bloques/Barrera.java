package model.Bloques;

import model.Casillas.CasillaTablero;
import model.Casillas.Explosion;

public class Barrera extends Bloque {
	
	public Barrera() {
	}

	
	
	//METODOS CASILLA
	@Override
	public boolean puedeMoverse() {
		return false;
	}

	@Override
	public boolean esObjeto(String pTipo) {
		if(pTipo.equals("Barrera")) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void destruir(CasillaTablero pC, int pI, int pJ) {
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
