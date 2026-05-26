package model.BomberMans;

import model.Bloques.BloqueFactory;
import model.Bombas.BombaFactory;
import model.Casillas.CasillaTablero;
import model.Tableros.Tablero;

public class BomberManNegro extends BomberMan{
	private static BomberMan miBM = null;

	private BomberManNegro() {
		super();
		bombasMax=1;
	}
	
	public static BomberMan getBomberMan() {
		if(miBM==null) {
			miBM = new BomberManNegro();
    	}
		return miBM;
	}
	
	public void setBombas(int pNumBombas) {
		bombasMax=pNumBombas;
	}
	
	@Override
	public boolean puedeColocarBomba() {
    	if (this.bombas >= bombasMax || !vivo || conBomba || victoria || Tablero.getTablero().hayBombas()) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }
	@Override
    public void ponerBomba() {
		if(puedeColocarBomba()) {
			this.bombas++;
			conBomba=true;
	    	Tablero.getTablero().getCasilla(i, j).cambiarCasilla(BombaFactory.getBombaFactory().generate("BUltra", i, j));
		}
    }
	
	@Override
	public void destruir(CasillaTablero pC, int pI, int pJ) {
		if (!vivo) return;
		vivo=false;
		pC.cambiarCasilla(miBM);
		Tablero.getTablero().reiniciar();
	}
	
	@Override
	public void mover(int pI, int pJ) {
		if (conBomba) {
    		conBomba=false;
    	}
    	aumentarNum();
    	if (i!=pI || j!=pJ) {
    		CasillaTablero c=Tablero.getTablero().getCasilla(i, j);
    		if (c.esObjeto("Bomba")) {
    			antiguaBomba=true;
    			c.cambiarCasilla(miBM);
    			antiguaBomba=false;
    		}
    		else {
    			c.cambiarCasilla(BloqueFactory.getBloqueFactory().generate("Vacio"));
    		}
    	}
    	i = pI;
    	j = pJ;
    	Tablero.getTablero().getCasilla(i, j).cambiarCasilla(miBM);
	}
}
