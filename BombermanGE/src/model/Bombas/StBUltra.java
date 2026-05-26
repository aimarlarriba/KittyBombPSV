package model.Bombas;

import model.Casillas.CasillaTablero;
import model.Tableros.Tablero;

public class StBUltra implements StrategyBombas {
    private Tablero tablero = Tablero.getTablero();

    public StBUltra() {}

    @Override
    public void destruirBloques(int pI, int pJ) {
        int rM = 20; 
        tablero.procesarExplosion(pI, pJ);

        for (int rango = 1; rango <= rM; rango++) {
            if (!procesarCasilla(pI - rango, pJ)) break;
        }

        for (int rango = 1; rango <= rM; rango++) {
            if (!procesarCasilla(pI + rango, pJ)) break;
        }

        for (int rango = 1; rango <= rM; rango++) {
            if (!procesarCasilla(pI, pJ - rango)) break;
        }

        for (int rango = 1; rango <= rM; rango++) {
            if (!procesarCasilla(pI, pJ + rango)) break;
        }
    }

    //bloque duro o fuera de tablero
    private boolean procesarCasilla(int pI, int pJ) {
        CasillaTablero c = tablero.getCasilla(pI, pJ);
        if (c == null) return false;
        if (c.esObjeto("Duro")) return false;
        tablero.procesarExplosion(pI, pJ);
        return true;
    }
}
