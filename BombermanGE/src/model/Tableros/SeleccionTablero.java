package model.Tableros;

import java.util.Observable;

public class SeleccionTablero extends Observable{
	private static SeleccionTablero miSeleccionTablero = null;
	
	
	private SeleccionTablero() {
    }
	
	public static SeleccionTablero getSeleccionTablero() {
    	if(miSeleccionTablero==null) {
    		miSeleccionTablero = new SeleccionTablero();
    	}
    	return miSeleccionTablero;
    }

	public void cerrarPantalla() {
		// TODO Auto-generated method stub
		setChanged();
        notifyObservers(0);
        Seleccion.getSeleccion().apagarTipoTablero();
	}

	public void tableroSelecciondado(int pTablero) {
		// TODO Auto-generated method stub
        if (pTablero==1) {
        Seleccion.getSeleccion().setPantalla(pTablero);
        } else if(pTablero==2) {
        	Seleccion.getSeleccion().setPantalla(pTablero);
        } else if(pTablero==3) {
        	Seleccion.getSeleccion().setPantalla(pTablero);
        } else if(pTablero==4) {
        	Seleccion.getSeleccion().setPantalla(pTablero);
        }
        cerrarPantalla();
	}
}
