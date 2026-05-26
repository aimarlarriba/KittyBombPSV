package model.Bloques;

public class BloqueFactory {
	private static BloqueFactory miBF;
	
	private BloqueFactory() {
    }
    
    public static BloqueFactory getBloqueFactory() {
    	if(miBF==null) {
    		miBF = new BloqueFactory();
    	}
    	return miBF;
    }
    
    public Bloque generate(String pTipo) {
    	Bloque bloque = null;
    	if(pTipo.equals("Blando")){
    		bloque = new Blando();
    	}else if(pTipo.equals("Duro")){
    		bloque = new Duro();
    	}else if (pTipo.equals("Boss")) {
    		bloque = new BloqueBoss();
    	}else if (pTipo.equals("Vacio")) {
    		bloque = new Vacio();
    	}else if (pTipo.equals("Barrera")) {
    		bloque = new Barrera();
    	}
	    return bloque;
    }
}
