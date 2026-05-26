package model.Bloques;

import model.Casillas.CasillaTablero;
import model.Casillas.Explosion;

public class Blando extends Bloque {
	
	public Blando() {
	}

	
	
	//METODOS CASILLA
	@Override
	public boolean esObjeto(String pTipo) {
		if(pTipo.equals("Blando")) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean puedeMoverse() {
		return false;
	}
	
	@Override
	public void destruir(CasillaTablero pC, int pI, int pJ) {
		pC.cambiarCasilla(new Explosion(pI, pJ));
	}
	
	@Override
	public void vaciar(CasillaTablero pC) {
	}
	
	@Override
	public void mover(int pI, int pJ) {
	}
}
