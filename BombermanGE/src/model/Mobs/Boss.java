package model.Mobs;

import java.util.Timer;
import java.util.TimerTask;
import model.Tableros.Tablero;

public class Boss {
	
	private static Boss miBoss;
	private boolean vivo = true;
	private int vida = 3
			;
	private Timer timerEscudo = null;
	private Timer timerEspera = null;
	private int tiempoEspera = 2500;
	private int tiempoEscudo = 10000;
	private int tiempoRomperEscudo = 2000;
	
	private Boss() {
	}

	
	public static Boss getBoss() {
		if(miBoss==null) {
			miBoss = new Boss();
    	}
    	return miBoss;
	}
	
	public void empezar() {
		Tablero.getTablero().colocarBarrera();
		Tablero.getTablero().invocarMobs(1);
	}
	
	public int getVida() {
		return vida;
	}
		
	public void desactivarEscudo() {
		if (timerEscudo == null) {
			timerEscudo = new Timer();
	        TimerTask timerTask = new TimerTask() {
	            @Override
	            public void run() {
	               if (vivo) {
	                   	timerEscudo.cancel(); 
	                   	Tablero.getTablero().destruirBarrera();
	                   	timerEscudo = null; 
	                   	activarEscudo();
	               }
	               else if (vida==0) {
	            	   timerEscudo.cancel(); 
	            	   timerEscudo = null; 
	               }
	            }
	        };
	        timerEscudo.schedule(timerTask, tiempoRomperEscudo);
		}
    }
	
	public void activarEscudo() {
		if (timerEscudo == null) {
			timerEscudo = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                	if(!Tablero.getTablero().hayBombas()) {
                		if (vivo && vida!=0) {
	                    	timerEscudo.cancel();
	                    	timerEscudo = null; 
	                    	Tablero.getTablero().colocarBarrera();
	                    	if (vida==2) {
	            				Tablero.getTablero().invocarMobs(3);
	            			}else if(vida==1) {
	            				Tablero.getTablero().invocarMobs(5);
	            			}else {
	            				Tablero.getTablero().invocarMobs(1);
	            			}
	                    }
	                    else if (vida==0) {
	 	            	   timerEscudo.cancel(); 
	 	            	   timerEscudo = null; 
	                    }
                	}else {
                		timerEscudo.cancel(); 
                		timerEscudo = null;
                	}
                }
            };
            timerEscudo.schedule(timerTask, tiempoEscudo);
        }
	}
	
	public void restarVida() {
		vida--;
		if (vida>=1) {
			Tablero.getTablero().notificarQuitarVidaBoss();
			pasarRonda();
		}else if(vida==0) {
			Tablero.getTablero().ganar();
		}
		
	}
	
	private void pasarRonda() {
		if (timerEspera == null) {
			timerEspera = new Timer();
	        TimerTask timerTask = new TimerTask() {
	            @Override
	            public void run() {
	            	if (timerEscudo!=null) {
	    				timerEscudo.cancel();
	    				timerEscudo = null;
	    			}
	    			Tablero.getTablero().colocarBarrera();
	    			if (vida==2) {
	    				tiempoEscudo=7000;
	    				Tablero.getTablero().invocarMobs(3);
	    			}else if(vida==1) {
	    				tiempoEscudo=5000;
	    				Tablero.getTablero().invocarMobs(5);
	    			}
	    			timerEspera.cancel();
	    			timerEspera = null;
	            }
	        };
	        timerEspera.schedule(timerTask, tiempoEspera);
		}
			
	}
	
	public void reiniciar() {
		vida=3;
		vivo=true;
		if (timerEscudo!=null) {
			timerEscudo.cancel();
			timerEscudo = null;
		}
		if (timerEspera!=null) {
			timerEspera.cancel();
			timerEspera = null;
		}
	}
}
