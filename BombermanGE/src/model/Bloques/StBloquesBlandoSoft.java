package model.Bloques;

import java.util.Random;
import java.util.stream.IntStream;

import model.Tableros.Tablero;

public class StBloquesBlandoSoft implements StrategyBloquesBlando{
	private Tablero tablero = Tablero.getTablero();
	
	public StBloquesBlandoSoft() {}
	
	public void colocarBloquesBlandos() {
		Random r = new Random();
		IntStream.range(0, 11).forEach(i ->
		    IntStream.range(0, 17).forEach(j -> {
		        if (!(i == 0 && j == 0) && !(i == 0 && j == 1) && !(i == 1 && j == 0)) {
		            if (r.nextBoolean()) {
		                tablero.setBloqueBlando(i, j);
		            }
		        }
		    })
		);
	 }
}
