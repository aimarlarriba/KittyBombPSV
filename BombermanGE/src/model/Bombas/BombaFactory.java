package model.Bombas;

public class BombaFactory {
	private static BombaFactory miBF;
	
	private BombaFactory() {
    }
    
    public static BombaFactory getBombaFactory() {
    	if(miBF==null) {
    		miBF = new BombaFactory();
    	}
    	return miBF;
    }
    
    public Bomba generate(String pTipo, int pI, int pJ) {
    	Bomba bomba = null;
    	if(pTipo.equals("BSuper")){
    		bomba = new BSuper(pI,pJ);
    	}else if(pTipo.equals("BUltra")){
    		bomba = new BUltra(pI,pJ);
    	}
    	bomba.activar();
    	return bomba;
    }
}
