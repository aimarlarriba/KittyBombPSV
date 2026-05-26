package model.Tableros;

import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;
import model.Bloques.BloqueFactory;
import model.Bloques.StBloquesBlandoClassic;
import model.Bloques.StBloquesBlandoSoft;
import model.Bloques.StBloquesDuroBoss;
import model.Bloques.StBloquesDuroClassic;
import model.Bloques.StrategyBloquesBlando;
import model.Bloques.StrategyBloquesDuro;
import model.Bombas.StBSuper;
import model.Bombas.StBUltra;
import model.Bombas.StrategyBombas;
import model.BomberMans.BomberMan;
import model.BomberMans.BomberManFactory;
import model.Casillas.CasillaTablero;
import model.Casillas.Invocacion;
import model.Mobs.Boss;
import model.Mobs.Enemigo;

@SuppressWarnings({"deprecation"})
public class Tablero extends Observable{

	private static final int fila = 11;
    private static final int col = 17;
    private static Tablero miTablero = null;
    private CasillaTablero[][] matriz = new CasillaTablero[fila][col];
    private StrategyBloquesDuro stBloquesDuro;
    private StrategyBloquesBlando stBloquesBlando;
    private StrategyBombas stBombas;
    private int numEnemigos;
    private static int bMSelec;
    private static String bMColor;
    private int pantalla;
    private boolean seleccionado=false;

    private Tablero() {
    	IntStream.range(0, fila).forEach(i ->
        IntStream.range(0, col).forEach(j ->
            matriz[i][j] = new CasillaTablero(i, j)
        )
    	);
    	numEnemigos = 0;
    }
    
    public void setTablero(int pBMSelec, int pPantalla) {
    	seleccionado=true;
    	bMSelec=pBMSelec;
		pantalla=pPantalla;
    	if (bMSelec==2) {
    		bMColor="Blanco";
    		changeStrategyBomba(new StBSuper());
    		getBomberman().setBombas(10);
    	}else if (bMSelec==1){
    		bMColor="Negro";
    		changeStrategyBomba(new StBUltra());
    		getBomberman().setBombas(1);
    		if (pantalla==4) {
    			getBomberman().setBombas(10);
    			changeStrategyBomba(new StBSuper());
    		}
    	}
	}
    
    public void initializeTablero() {
    	if (seleccionado) {
    		if (pantalla==1) {
        		crearClassic();
        	} else if(pantalla==2) {
        		crearSoft();
        	}else if(pantalla==3) {
        		crearEmpty();
        	}else if(pantalla==4) {
        		crearBoss();
        	}
    	}
    }
    
    //pPantalla = 1
	public void crearClassic() {
		changeStrategyBloquesBlando(new StBloquesBlandoClassic());
		changeStrategyBloquesDuro(new StBloquesDuroClassic());
		vaciarTablero();
		colocarBomberMan();
		colocarBloquesDuros();
		colocarBloquesBlandos();
		colocarEnemigos(8);
	}
	
    //pPantalla = 2
	public void crearSoft() {
		changeStrategyBloquesBlando(new StBloquesBlandoSoft());
		vaciarTablero();
		colocarBomberMan();
		colocarBloquesBlandos();
		colocarEnemigos(8);
	}
	
    //pPantalla = 3
	public void crearEmpty() {
		vaciarTablero();
		colocarBomberMan();
		colocarEnemigos(8);
	}
	
	//pPantalla = 4
	public void crearBoss() {
		changeStrategyBloquesDuro(new StBloquesDuroBoss());
		vaciarTablero();
		colocarBomberMan();
		colocarBloquesDuros();
		Boss.getBoss().empezar();
	}
    
	//CONSTRUCCION DEL TABLERO
    public void colocarBomberMan() {
    	matriz[0][0].cambiarCasilla(BomberManFactory.getBomberManFactory().generate("BomberMan"+bMColor));
    	getBomberman().setDir('d');
		getBomberman().mover(0,0);
		while (getBomberman().getNum()!=1) {
			getBomberman().aumentarNum();
		}
    }
    
    public void colocarBloquesDuros() {
    	stBloquesDuro.colocarBloquesDuros();
    }
    
    public void colocarBloquesBlandos() {
    	stBloquesBlando.colocarBloquesBlandos();
    }
    
    public void setBloqueDuro(int pI, int pJ) {
    	matriz[pI][pJ].cambiarCasilla(BloqueFactory.getBloqueFactory().generate("Duro"));
    }
    
    public void setInvocacion(int pI, int pJ) {
    	matriz[pI][pJ].cambiarCasilla(new Invocacion(pI,pJ));
    }
    
    public void setBloqueBoss(int pI, int pJ) {
    	matriz[pI][pJ].cambiarCasilla(BloqueFactory.getBloqueFactory().generate("Boss"));
	}
    
    public void setBloqueBlando(int pI, int pJ) {
    	matriz[pI][pJ].cambiarCasilla(BloqueFactory.getBloqueFactory().generate("Blando"));
    }
    
    public void setBarrera(int pI, int pJ) {
    	matriz[pI][pJ].cambiarCasilla(BloqueFactory.getBloqueFactory().generate("Barrera"));
    }
    
    public void vaciarTablero() {
    	IntStream.range(0, fila).forEach(i ->
        IntStream.range(0, col).forEach(j ->
            matriz[i][j].cambiarCasilla(BloqueFactory.getBloqueFactory().generate("Vacio"))
        )
    	);
    }
    
    public void  changeStrategyBloquesDuro(StrategyBloquesDuro stB){
    	stBloquesDuro = stB;
    }
    
    public void changeStrategyBloquesBlando(StrategyBloquesBlando stB){
    	stBloquesBlando = stB;
    }
    
    public void changeStrategyBomba(StrategyBombas stB){
    	stBombas = stB;
    }

    public void colocarEnemigos(int num) {
		Random r = new Random();
		while (numEnemigos < num) {
		    int i = r.nextInt(fila);
		    int j = r.nextInt(col);	
		    if (!(i == 0 && j == 0) && !(i == 0 && j == 1) && !(i == 1 && j == 0)
		        && matriz[i][j].esObjeto("Vacio") && !matriz[i][j].esObjeto("Barrera")) {
		    	matriz[i][j].cambiarCasilla(new Enemigo(i,j));
		    	numEnemigos++;
		    }
		}
		setChanged();
		notifyObservers(new Object[] {1,numEnemigos});
	}
    
    public int getNumEnemigos() {
    	return numEnemigos;
    }
    
    
    
    //METODOS GET
    public static Tablero getTablero() {
    	if(miTablero==null) {
    		miTablero = new Tablero();
    	}
    	return miTablero;
    }
    
    public int getPantalla() {
		return pantalla;
	}
    
    public BomberMan getBomberman() {
		if(bMSelec==1) {
    		return BomberManFactory.getBomberManFactory().generate("BomberManNegro");
    	}else {
    		return BomberManFactory.getBomberManFactory().generate("BomberManBlanco");
    	}
    }
    
    public boolean esBomberman(int pI, int pJ) {
    	if (getBomberman().getI()==pI && getBomberman().getJ()==pJ) {
    		return true;
    	}
    	return false;
    }
    
    public CasillaTablero getCasilla(int pI, int pJ) {
    	CasillaTablero c = null;
    	if (pI>=0 && pJ>=0 && pI<11 && pJ<17) {
    		c = matriz[pI][pJ];
    	}
    	return c;
    }
   
    
    
    //MOVIMIENTO ENEMIGO
    public void moverEnemigo(int pI, int pJ) {
        if (pI < 0 || pI >= fila || pJ < 0 || pJ >= col) return;
        if (!matriz[pI][pJ].esObjeto("Enemigo")) return;
        if (!getBomberman().isVivo()) return;
        CasillaTablero c = matriz[pI][pJ];
        if (!c.isVivo()) return;
        int nuevoI = c.getI();
        int nuevoJ = c.getJ();
        int bombermanI = getBomberman().getI();
        int bombermanJ = getBomberman().getJ();
        if (bombermanI == nuevoI - 1 && bombermanJ == nuevoJ && movimientoEnemigo(nuevoI - 1, nuevoJ)) {
            nuevoI--; 
        } else if (bombermanI == nuevoI + 1 && bombermanJ == nuevoJ && movimientoEnemigo(nuevoI + 1, nuevoJ)) {
            nuevoI++; 
        } else if (bombermanI == nuevoI && bombermanJ == nuevoJ - 1 && movimientoEnemigo(nuevoI, nuevoJ - 1)) {
            nuevoJ--; 
        } else if (bombermanI == nuevoI && bombermanJ == nuevoJ + 1 && movimientoEnemigo(nuevoI, nuevoJ + 1)) {
            nuevoJ++; 
        } else {
            Random random = new Random();
            String[] direcciones = {"arriba", "abajo", "izquierda", "derecha"};
            String direccion = direcciones[random.nextInt(4)];
            if (direccion.equals("arriba") && movimientoEnemigo(nuevoI - 1, nuevoJ)) {
                nuevoI--;
            } else if (direccion.equals("abajo") && movimientoEnemigo(nuevoI + 1, nuevoJ)) {
                nuevoI++;
            } else if (direccion.equals("izquierda") && movimientoEnemigo(nuevoI, nuevoJ - 1)) {
                nuevoJ--;
            } else if (direccion.equals("derecha") && movimientoEnemigo(nuevoI, nuevoJ + 1)) {
                nuevoJ++;
            }
        }       
        if (nuevoI == bombermanI && nuevoJ == bombermanJ) {
            matriz[bombermanI][bombermanJ].destruir();
        } else if (nuevoI != c.getI() || nuevoJ != c.getJ()) { // Movimiento normal
        	c.mover(nuevoI,nuevoJ);
        }
    }
    
    public boolean movimientoEnemigo(int pI, int pJ) {
    	if(pI<0 || pI >=11 || pJ<0 || pJ>=17) return false;
    	CasillaTablero c=getCasilla(pI,pJ);
    	if ((pI == 0 && pJ == 0) || (pI == 0 && pJ == 1) || (pI == 1 && pJ == 0) || 
    		c.esObjeto("Enemigo") || c.esObjeto("Explosion")) {
    		return false;
    	}
    	return c.puedeMoverse();
    }
 
    
    
    //MOVIMIENTO BOMBERMAN
	public void moverBomberman(char pDir) {
	    if (getBomberman().isVivo() && !getBomberman().haGanado()) {
	        int pI = getBomberman().getI();
	        int pJ = getBomberman().getJ();
	        int prevI=pI;
	        int prevJ=pJ;
	        BomberMan cB = getBomberman();
	        CasillaTablero c;
	        if (pDir == 'u') {
	        	if (!(pI-1<0)) {
		            if (movimientoPosible(pI - 1, pJ) || matriz[pI - 1][pJ].esObjeto("Enemigo") || matriz[pI - 1][pJ].esObjeto("Boss")) {
		                c = matriz[pI - 1][pJ];
		                if (c.esObjeto("Explosion")) {
		                	cB.mover(pI - 1, pJ);
		                    matriz[pI - 1][pJ].destruir();
		                } else if (c.esObjeto("Enemigo")||c.esObjeto("Boss")) {
		                	matriz[pI][pJ].destruir();
		                } else {
		                	getBomberman().setDir(pDir);
		                	cB.mover(pI - 1, pJ);
		                }
		            }
	        	}
	        } else if (pDir == 'd') {
	        	if (!(pI+1>10)) {
		            if (movimientoPosible(pI + 1, pJ) || matriz[pI + 1][pJ].esObjeto("Enemigo") || matriz[pI + 1][pJ].esObjeto("Boss")) {
		                c = matriz[pI + 1][pJ];
		                if (c.esObjeto("Explosion")) {
		                	cB.mover(pI + 1, pJ);
		                    matriz[pI + 1][pJ].destruir();
		                } else if (c.esObjeto("Enemigo")||c.esObjeto("Boss")) {
		                	matriz[pI][pJ].destruir();
		                } else {
		                	getBomberman().setDir(pDir);
		                	cB.mover(pI + 1, pJ);
		                }
		            }
	        	}
	        } else if (pDir == 'r') {
	        	if (!(pJ+1>16)) {
		            if (movimientoPosible(pI, pJ + 1) || matriz[pI][pJ + 1].esObjeto("Enemigo") || matriz[pI][pJ + 1].esObjeto("Boss")) {
		                c = matriz[pI][pJ + 1];
		                if (c.esObjeto("Explosion")) {
		                	cB.mover(pI, pJ + 1);
		                    matriz[pI][pJ + 1].destruir();
		                } else if (c.esObjeto("Enemigo")||c.esObjeto("Boss")) {
		                	matriz[pI][pJ].destruir();
		                } else {
		                	getBomberman().setDir(pDir);
		                	cB.mover(pI, pJ + 1);
		                }
		            }
	        	}
	        } else if (pDir == 'l') {
	        	if (!(pJ-1<0)) {
		            if (movimientoPosible(pI, pJ - 1) || matriz[pI][pJ - 1].esObjeto("Enemigo") || matriz[pI][pJ - 1].esObjeto("Boss")) {
		                c = matriz[pI][pJ - 1];
		                if (c.esObjeto("Explosion")) {
		                	cB.mover(pI, pJ - 1);
		                    matriz[pI][pJ - 1].destruir();
		                } else if (c.esObjeto("Enemigo")||c.esObjeto("Boss")) {
		                	matriz[pI][pJ].destruir();
		                } else {
		                	getBomberman().setDir(pDir);
		                	cB.mover(pI, pJ - 1);
		                }
		            }
	        	}
	        }
	        if ((prevI==getBomberman().getI() && prevJ==getBomberman().getJ()) && !getBomberman().conBomba()) {
            	getBomberman().setDir(pDir);
            	cB.mover(pI, pJ);
            }
	    }
	}
	
    public boolean movimientoPosible(int pI, int pJ) {
    	boolean posible = true;
    	if(pI<0 || pI >=11 || pJ<0 || pJ>=17) {
    		posible = false;
    	}else{
    		CasillaTablero c = matriz[pI][pJ];
    		if (c.esObjeto("BomberMan")) {
    	        return true;
    	    }
    		posible = c.puedeMoverse();
    		if (c.esObjeto("Barrera")) {
    		}
    		
    	}
    	return posible;
    }
    
    
    
    //BOMBA
    public void colocarBomba() {
    	int pI = getBomberman().getI();
    	int pJ = getBomberman().getJ();
        if (getBomberman().puedeColocarBomba()) {
        	CasillaTablero c = matriz[pI][pJ];
            if (c.esObjeto("Bomba")) {
                return;
            }
            getBomberman().ponerBomba();
        }
    }
    
    
    
    //EXPLOSION
    public void destruirBloques(int pI, int pJ) {
		getBomberman().bombaExplotada();
		if(stBombas!=null) {
			stBombas.destruirBloques(pI,pJ);
		}
        
    }
    
    public void procesarExplosion(int pI, int pJ) {
    	if (pI < 0 || pI >= fila || pJ < 0 || pJ >= col) return;
    	CasillaTablero c = matriz[pI][pJ];
    	if (c.esObjeto("Barrera")) {
    		return;
    	}
    	else if (c.esObjeto("Enemigo")) {
	    	numEnemigos--;
	    	setChanged();
			notifyObservers(new Object[] {1,numEnemigos});
	    	if (numEnemigos == 0 && pantalla == 4) {
	    		Boss.getBoss().desactivarEscudo();
	    	}
	    	else if (numEnemigos == 0 && pantalla != 4) {
	    		ganar();
	    	}
	    }
	    c.destruir();
	    if (esBomberman(pI,pJ)) {
    		getBomberman().destruir(c,pI,pJ);
    		getBomberman().mover(pI, pJ);
    	}
    }    

    public void apagar(int pI, int pJ) {
    	if (pI < 0 || pI >= fila || pJ < 0 || pJ >= col) return;
    	if (esBomberman(pI,pJ)) return;
    	CasillaTablero c = matriz[pI][pJ];
    	c.vaciar();
    }
    
    

    //BOSS
    public void destruirBarrera() {
    	matriz[5][6].destruir();
    	matriz[3][8].destruir();
    	matriz[7][8].destruir();
    	matriz[5][10].destruir();
    	setChanged();
		notifyObservers(new Object[] {4,2});
	}
    
    public boolean hayBombas() {
    	if(pantalla==4) {
    		if (matriz[5][6].esObjeto("Bomba") || matriz[5][6].esObjeto("Explosion")) {
	    		return true;
	    	}else if (matriz[3][8].esObjeto("Bomba") || matriz[3][8].esObjeto("Explosion")) {
	    		return true;
	    	}else if (matriz[7][8].esObjeto("Bomba") || matriz[7][8].esObjeto("Explosion")){
	    		return true;
	    	}else if (matriz[5][10].esObjeto("Bomba") || matriz[5][10].esObjeto("Explosion")){
	    		return true;
	    	}
    	}
    	return false;
	}
    
    public void notificarQuitarVidaBoss() {
    	setChanged();
		notifyObservers(new Object[] {4,3});
	}
    
    public void colocarBarrera() {
    	if(esBomberman(5,6)) {
    		getBomberman().mover(5, 5);
    	}
    	setBarrera(5,6);
    	if(esBomberman(3,8)) {
    		getBomberman().mover(2, 8);
    	}
    	setBarrera(3,8);
    	if(esBomberman(7,8)) {
    		getBomberman().mover(8, 8);
    	}
    	setBarrera(7,8);
    	if(esBomberman(5,10)) {
    		getBomberman().mover(5, 11);
    	}
    	setBarrera(5,10);
    }
    
    public void invocarMobs(int num) {
    	setChanged();
		notifyObservers(new Object[] {4,1});
    	Random r = new Random();
	    while (numEnemigos < num) {     
	        int i = r.nextInt(fila);  
	        int j = r.nextInt(col);   
	        if (!(i == 0 && j == 0) && !(i == 0 && j == 1) && !(i == 1 && j == 0) && 
	        Tablero.getTablero().getCasilla(i, j).esObjeto("Vacio") && !Tablero.getTablero().getCasilla(i, j).esObjeto("Barrera")) {
	        	Tablero.getTablero().setInvocacion(i, j);
	            numEnemigos++;
	        }
	    }
	    setChanged();
		notifyObservers(new Object[] {1,numEnemigos});
		
	}
    
    
    
    //METODOS FIN JUEGO
    public void ganar() {
    	if (pantalla==4) {
    		setChanged();
			notifyObservers(new Object[] {4,4});
    	}
    	Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
        	BomberMan bm = getBomberman();
        	int contador = 1;
            @Override
            public void run() {
            	if (contador==3) {
	            	bm.setVictoria(true);
	            	bm.mover(bm.getI(), bm.getJ());
	            	contador++;
            	}
            	else if (contador==4) {
            		if (pantalla!=4) {
	            		bm.setVictoria(false);
	            		pantalla=4;
	            		setTablero(bMSelec, pantalla);
	            		initializeTablero();
	            		setChanged();
	    				notifyObservers(new Object[] {2,0});
	    				timer.cancel();
            		}
            		else {
            			reiniciar();
            			timer.cancel();
            		}
            	}
            	else {
            		contador++;
            	}
            }
        }, 0, 2000);
    }
    
    public void reiniciar() {
        Timer timer = new Timer();
        BomberMan bm = getBomberman();
        if (pantalla==4 && !bm.isVivo()) {
    		setChanged();
			notifyObservers(new Object[] {4,5});
    	}
        final int[] contador = {0};
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (contador[0] == 3) {
                    bm.setVictoria(false);
                    bm.setVivo();
                    vaciarTablero();
                    numEnemigos = 0;
                    seleccionado = false;
                    if (bMSelec==1){
                    	getBomberman().setBombas(1);
                    }
                    setChanged();
                    notifyObservers(new Object[] {3, numEnemigos});
					Seleccion.getSeleccion().reiniciarJuego();
                    Boss.getBoss().reiniciar();
                    this.cancel();
                    timer.cancel();
                } else {
                    contador[0]++;
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

}
