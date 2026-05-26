package model.Bloques;

import java.util.stream.IntStream;

import model.Tableros.Tablero;

public class StBloquesDuroClassic implements StrategyBloquesDuro {
	private Tablero tablero = Tablero.getTablero();
	
	public StBloquesDuroClassic() {}
	
	@Override
	public void colocarBloquesDuros() {
		IntStream.range(0, 11).forEach(i ->
	    IntStream.range(0, 17).forEach(j -> {
		        if (!(i == 0 && j == 0) && !(i == 0 && j == 1) && !(i == 1 && j == 0)) {
		            if (i % 2 != 0 && j % 2 != 0) {
		                tablero.setBloqueDuro(i, j);
		            }
		        }
		    })
		);
	}
}
