
package viewController.Pantallas;

import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Casillas.CasillaTablero;
import model.Tableros.Tablero;

@SuppressWarnings({"deprecation"})
public class PanelCord extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private int i;
	private int j;
	private JLabel Label;
	private boolean alternarBaloon = false;
	private String bmColor;

	public PanelCord(int pI, int pJ, String pBMColor) {
		this.i = pI;
		this.j = pJ;
		this.bmColor = pBMColor;
		this.setOpaque(false);
		add(getLabel());
		this.setSize(getPreferredSize());
	}
	
	public int getI() {
		return this.i;
	}
	
	public int getJ() {
		return this.j;
	}
	
	public JLabel getLabel() {
		if (Label == null) {
			Label = new JLabel("");
		}
		return Label;
	}
	
	public void setObserver(int pI, int pJ) {
		Tablero.getTablero().getCasilla(pI,pJ).addObserver(this);
	}
	
	 //UPDATE OBSERVER

		@Override
		public void update(Observable o, Object arg) {
			// TODO Auto-generated method stub
			if(o instanceof CasillaTablero) {
				Object res[]=(Object[])arg;
				String opcion = (String) res[0];
				int num = (int) res[1];
				switch (opcion) {
				 case "Morir":
		                setLabel("/viewController/Bomberman/"+ bmColor+ "OnFire" + num + ".png");  
		                break;
				 case "Ganar":
		                setLabel("/viewController/Bomberman/"+ bmColor+ "happy" + num + ".png");  
		                break;
				 case "Vacio":
		                clearLabel();
		                break;
				 case "Boss":
		                clearLabel();
		                break;
		        case "Enemigo":
		        		if (num==1) {
		        			String rutaen = alternarBaloon
				                    ? "/viewController/Objetos/baloon1.png"
				                    : "/viewController/Objetos/baloon2.png";
				                setLabel(rutaen);
				                alternarBaloon = !alternarBaloon;
				                break;
		        		} else {
		        			String rutaen = alternarBaloon
				                    ? "/viewController/Objetos/queso1.png"
				                    : "/viewController/Objetos/queso2.png";
				                setLabel(rutaen);
				                alternarBaloon = !alternarBaloon;
				                break;
		        		}
		                
	            case "u":
	            	setLabel("/viewController/Bomberman/"+bmColor+"up"+num+".png");
	            	break;
	            case "d":
	            	setLabel("/viewController/Bomberman/"+bmColor+"down"+num+".png");
	            	break;
	            case "l":
	            	setLabel("/viewController/Bomberman/"+bmColor+"left"+num+".png");
	            	break;
	            case "r":
	            	setLabel("/viewController/Bomberman/"+bmColor+"right"+num+".png");
	            	break;
	            case "BmBomba":
	            	setLabel("/viewController/Bomberman/"+bmColor+"withbomb" + num + ".png");
	            	break;
	            case "Bomba":
	                setLabel("/viewController/Objetos/bomb" + num + ".png");
	                break;
	            case "Duro":
	            	setLabel("/viewController/Objetos/hard"+ num +".png");
	            	break;
	            case "Blando":
	            	setLabel("/viewController/Objetos/soft4.png");
	            	break;
	            case "Explosion":
	            	setLabel("/viewController/Objetos/miniBlast1.gif");
	            	break;
	            case "Barrera":
	            	setLabel("/viewController/Objetos/barrera"+num+".png");
	            	break;
	            case "Invocacion":
	            	setLabel("/viewController/Objetos/invocacion"+num+".gif");
	            	break;
				}
			}
	    }
		
		public void setLabel(String pTipo) {
			if (pTipo!=null) {
				Label.setIcon(new ImageIcon(PanelCord.class.getResource(pTipo)));
			}
		}
		
		public void clearLabel() {
			Label.setIcon(null);
		}
}
