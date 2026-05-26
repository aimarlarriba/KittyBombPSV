package model.Bloques;

import model.Casillas.Casilla;

public abstract class Bloque implements Casilla {
	
	protected Bloque(){
	}
	

	
	//METODOS CASILLA
	@Override
	public boolean isVivo() {
    	return false;
    }
}
