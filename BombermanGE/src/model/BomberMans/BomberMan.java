package model.BomberMans;

import model.Casillas.Casilla;
import model.Casillas.CasillaTablero;

public abstract class BomberMan implements Casilla {
	protected int i=0;
	protected int j=0;
	protected int bombas=0;
	protected boolean vivo=true;
	protected boolean conBomba=false;
	protected boolean antiguaBomba=false;
	protected boolean victoria=false;
	protected char dir = 'd' ;
	protected int num = 1;
	protected int bombasMax=0;
	
	protected BomberMan() {
	}

	
	//METODOS GET O SIMILAR
	public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
    
    public int getNum() {
        return num;
    }
    
    public char getDir() {
    	return dir;
    }
    
    public boolean conBomba() {
    	return conBomba;
    }
    
	public boolean antiguaBomba() {
		if (antiguaBomba) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean haGanado() {
    	return victoria;
    }
    
    public abstract boolean puedeColocarBomba();
    
    public abstract void setBombas(int pNumBombas);
    
    //METODOS SET O SIMILAR
    public void setDir(char pD) {
    	if (pD!=dir) {
    		dir=pD;
    		num=0;
    	}
    }
    
    public void aumentarNum() {
    	num++;
    	if (num==6) num=1;
    }
    
    public abstract void ponerBomba();
    
    public void bombaExplotada() {
    	bombas--;
    }
    
    public void setVictoria(boolean pWin) {
    	victoria=pWin;
    }
    
    public void setVivo() {
    	vivo=true;
    }
    
	
    
	//METODOS CASILLA
	@Override
	public boolean puedeMoverse() {
		return true;
	}
	
	@Override
	public boolean esObjeto(String pTipo) {
		if(pTipo.equals("BomberMan")) {
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
    	return vivo;
    }

	
	@Override
	public void mover(int pI, int pJ) {
	}
}
