package model.Bloques;

import model.Casillas.CasillaTablero;
import model.Casillas.Explosion;

public class Vacio extends Bloque {

	public Vacio() {
	}

	
	
	//METODOS CASILLA
	@Override
	public boolean puedeMoverse() {
		return true;
	}

	@Override
	public boolean esObjeto(String pTipo) {
		if(pTipo.equals("Vacio")) {
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
	public void mover(int pI, int pJ) {
	}
}
