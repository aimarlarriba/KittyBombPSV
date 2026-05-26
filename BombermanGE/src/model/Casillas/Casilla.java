package model.Casillas;

public interface Casilla {
	
	public boolean puedeMoverse();
	public boolean esObjeto(String pTipo);
	public void destruir(CasillaTablero pC,int pI, int pJ);
	public void vaciar(CasillaTablero pC);
	public boolean isVivo();
	public void mover(int pI, int pJ);
}
