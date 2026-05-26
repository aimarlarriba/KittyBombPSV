package model.Bloques;

import model.Casillas.CasillaTablero;
import model.Mobs.Boss;

public class BloqueBoss extends Duro {

	public BloqueBoss() {
	}
	
	@Override
	public boolean puedeMoverse() {
		return false;
	}

	@Override
	public boolean esObjeto(String pTipo) {
		if(pTipo.equals("Boss")) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void destruir(CasillaTablero pC, int pI, int pJ) {
		Boss.getBoss().restarVida();
	}

	@Override
	public void vaciar(CasillaTablero pC) {
	}

	@Override
	public void mover(int pI, int pJ) {
	}

}
