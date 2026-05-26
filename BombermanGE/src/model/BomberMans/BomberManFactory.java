package model.BomberMans;

public class BomberManFactory {
	private static BomberManFactory miBF;
	
	private BomberManFactory() {
    }
    
    public static BomberManFactory getBomberManFactory() {
    	if(miBF==null) {
    		miBF = new BomberManFactory();
    	}
    	return miBF;
    }
    
    public BomberMan generate(String pTipo) {
    	BomberMan bm = null;
    	if(pTipo.equals("BomberManBlanco")){
    		bm = BomberManBlanco.getBomberMan();
    	}else if(pTipo.equals("BomberManNegro")){
    		bm = BomberManNegro.getBomberMan();
    	}
    	return bm;
    }
}
