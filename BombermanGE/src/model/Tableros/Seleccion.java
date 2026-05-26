package model.Tableros;
import java.util.Observable;

@SuppressWarnings({"deprecation"})
public class Seleccion extends Observable{
	private static Seleccion miPantallaInicio = null;
	private static final int numBM = 2; //numero de bombermans a elegir
	private static int BMSelec;
	private static int pantallaSelec;
    private static boolean yaSeleccionado;
	
	private Seleccion() {
		BMSelec= 0;
		yaSeleccionado = false;
		pantallaSelec=1;
    }
    
    public static Seleccion getSeleccion() {
    	if(miPantallaInicio==null) {
    		miPantallaInicio = new Seleccion();
    	}
    	return miPantallaInicio;
    }
    
    public void seleccionarBomberMan(char pDir) {
    	yaSeleccionado=true;
    	if(BMSelec==0) {
    		if (pDir=='l') {
    			BMSelec++;
    		}else if(pDir=='r') {
    			BMSelec=numBM;
    		}
    	}
    	if (pDir=='l') {
    		if (BMSelec<numBM) {
    			BMSelec++;
    			
    		} else if(BMSelec==numBM) {
    			BMSelec=1;
    			
    		}
    	} else if(pDir=='r') {
    		if (BMSelec>1) {
    			BMSelec--;
    			
    		} else if(BMSelec==1) {
    			BMSelec=numBM;
    		}
    	}
    	setChanged();
        notifyObservers(new Object[] {BMSelec, false});
    }

	public void inicializarTablero() {
		if (yaSeleccionado) {
			Tablero.getTablero().setTablero(BMSelec,pantallaSelec);
			//VISTA//
			if (BMSelec==1) {
				setChanged();
		        notifyObservers(new Object[] {0, false});
			} else {
				setChanged();
		        notifyObservers(new Object[] {3, false});
			}
		}
	}

	
	public void seleccionarTipoTablero() {
		// TODO Auto-generated method stub
		SeleccionTablero.getSeleccionTablero();
		setChanged();
        notifyObservers(new Object[] {4, false});
	}

	public void apagarTipoTablero() {
		// TODO Auto-generated method stub
		setChanged();
        notifyObservers(new Object[] {5, false});
	}
	
	public void setPantalla(int pTablero) {
		// TODO Auto-generated method stub
		pantallaSelec=pTablero;
		if (pTablero==1) {
			setChanged();
	        notifyObservers(new Object[] {10, false});
		} else if (pTablero==2) {
			setChanged();
	        notifyObservers(new Object[] {20, false});
		}else if (pTablero==3) {
			setChanged();
	        notifyObservers(new Object[] {30, false});
		} else if(pTablero==4) {
			setChanged();
	        notifyObservers(new Object[] {40, false});
        }
	}
	
	public void reiniciarJuego() {
		setChanged();
        notifyObservers(new Object[] {BMSelec, true});
	}
}
