package model.Bloques;

import java.util.stream.IntStream;

import model.Tableros.Tablero;

public class StBloquesDuroBoss implements StrategyBloquesDuro {
	private Tablero tablero = Tablero.getTablero();
	private int [][] matrizBloques;
	
	public StBloquesDuroBoss() {
		this.matrizBloques =  
			 new int[][] 
			  {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			   {0,1,0,0,1,1,1,0,0,0,1,1,1,0,0,1,0},
			   {0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
			   {0,0,1,0,1,0,1,1,0,1,1,0,1,0,1,0,0},
			   {0,0,1,0,0,0,1,2,2,2,1,0,0,0,1,0,0},
			   {0,0,1,0,0,0,0,2,2,2,0,0,0,0,1,0,0},
			   {0,0,1,0,0,0,1,2,2,2,1,0,0,0,1,0,0},
			   {0,0,1,0,1,0,1,1,0,1,1,0,1,0,1,0,0},
	  		   {0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
			   {0,1,0,0,1,1,1,0,0,0,1,1,1,0,0,1,0},
			   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
	}
	
	@Override
	public void colocarBloquesDuros() {
		IntStream.range(0, 11).forEach(i ->
	    IntStream.range(0, 17).forEach(j -> {
		        if (matrizBloques[i][j] == 1) {
		            tablero.setBloqueDuro(i, j);
		        } else if (matrizBloques[i][j] == 2) {
		            tablero.setBloqueBoss(i, j);
		        }
	    	})
		);
	}
}
