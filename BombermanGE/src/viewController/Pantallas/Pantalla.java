package viewController.Pantallas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Tableros.Tablero;

@SuppressWarnings({"deprecation"})
public class Pantalla extends JFrame implements Observer{

    private static final long serialVersionUID = 1L;
    private static final Integer BACKGROUND_LAYER = -100;
    private JLayeredPane contentPane;
    private JPanel gridPanel;
    private JLabel enemigosLabel;
    private JLabel fondoPermanente;
    private JLabel fondoTemporal;
	private Controlador controlador;
	private static int bMSelec;
	private static int numEnemigos;
	private static String bMColor;
	private static int pantallaSelec=1;
	
    public Pantalla(int pBMSelec,int pPantalla) {
    	setFondoPantalla(pPantalla);
    	setBM(pBMSelec);
    	setEnemigos(getEnemigos());
    	initialize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setObservers();
    	getControlador().actionPerformed(null);
    	if (pPantalla==4) {
    		setEnemigosLabel(0);
    	}
    }
    private void setBM(int pBMSelec) {
    	bMSelec=pBMSelec;
    	if (bMSelec==2) {
    		bMColor="white";
    	}else {
    		bMColor="black";
    	}
	}
    
    private void setFondoPantalla(int pPantalla) {
    	pantallaSelec=pPantalla;	
	}
    
    private void initialize() {
        setBounds(100, 100, 850, 550);
        setResizable(false);
        setLocationRelativeTo(null);
        addKeyListener(getControlador());
        setVisible(true);

        setContentPane(getContentPane());

        if(pantallaSelec==4) {
        	ponerGifDeFondo("viewController/Imagenes/stageBack4.png");
        }else {
        	ponerImagenDeFondo("viewController/Imagenes/stageBack" + pantallaSelec + ".png");
        }
        
        for (int i = 0; i < 11; i++) { 
            for (int j = 0; j < 17; j++) { 
            	PanelCord panel = new PanelCord(i, j, bMColor);
                panel.setPreferredSize(null);
                gridPanel.add(panel);
            }
        }
    }
    
    private void setObservers() {
    	for (int i = 0; i < 11; i++) { 
            for (int j = 0; j < 17; j++) { 
            	PanelCord panel = (PanelCord) gridPanel.getComponent(i*17+j);
        		panel.setObserver(i,j);
            }
        }
    	Tablero.getTablero().addObserver(this);
    }
   
 // FONDO
    public JLayeredPane getContentPane() {
        if (contentPane == null) {
            contentPane = new JLayeredPane();
            contentPane.setLayout(null);
            gridPanel = new JPanel(new GridLayout(11, 17, 0, 0));
            gridPanel.setOpaque(false);
            gridPanel.setBounds(0, 0, 840, 520); 

            contentPane.add(gridPanel, JLayeredPane.PALETTE_LAYER);
        }
        return contentPane;
    }
    
    private void ponerGifDeFondo(String rutaGif) {
    	try {
    		if (fondoTemporal != null && fondoTemporal.getParent() == contentPane) {
                contentPane.remove(fondoTemporal);
                fondoTemporal = null;
            }
	    	byte[] gifBytes = getClass().getClassLoader().getResourceAsStream(rutaGif).readAllBytes();
	        ImageIcon gifIcon = new ImageIcon(gifBytes);
	        fondoTemporal = new JLabel(gifIcon);
	        fondoTemporal.setBounds(0, 0, 840, 520);
	        contentPane.add(fondoTemporal, JLayeredPane.DEFAULT_LAYER);
	        contentPane.revalidate();
	        contentPane.repaint();
	        System.gc();
    	} catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void ponerImagenDeFondo(String rutaImagen) {
        try {
        	if (fondoPermanente != null) {
        		contentPane.remove(fondoPermanente);
        		fondoPermanente=null;
        	}        		
        		ImageIcon icon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource(rutaImagen)));
        		if(pantallaSelec==4) {
        			fondoPermanente = new JLabel(icon);
        		} else {
        			Image imagen = icon.getImage().getScaledInstance(840, 520, Image.SCALE_SMOOTH);
        	        fondoPermanente = new JLabel(new ImageIcon(imagen));
        		}
                fondoPermanente.setBounds(0, 0, 840, 520);
                contentPane.add(fondoPermanente, BACKGROUND_LAYER);
                contentPane.revalidate();
                contentPane.repaint();
                
                
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void limpiarFondo() {
    	if (fondoTemporal != null && fondoTemporal.getParent() == contentPane) {
            contentPane.remove(fondoTemporal);
            fondoTemporal = null;
        }
    	

        if (enemigosLabel != null) {
            contentPane.remove(enemigosLabel);
        }
        enemigosLabel = null;
        contentPane.revalidate();
        contentPane.repaint();
        setEnemigosLabel(getEnemigos());
    }
    
    private void setEnemigosLabel(int numEnemigos) {
    	if (enemigosLabel == null) {
	    	enemigosLabel = new JLabel("Enemigos: " + numEnemigos); 
	    	enemigosLabel.setFont(new Font("Arial", Font.BOLD, 20));
	    	enemigosLabel.setForeground(Color.BLACK); 
	    	enemigosLabel.setBounds(640, 480, 180, 30);
	    	enemigosLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	    	contentPane.add(enemigosLabel, JLayeredPane.POPUP_LAYER); 
		}else {
			enemigosLabel.setText("Enemigos: " + numEnemigos);
		}
    }
    
    private void setEnemigos(int pNumEnemigos) {
    	numEnemigos=pNumEnemigos;
    }
    
    private int getEnemigos() {
    	return numEnemigos;
    }
    
    
    //UPDATE
    @Override
	public void update(Observable o, Object arg) {
		if(o instanceof Tablero) {
			Object res[]=(Object[])arg;
			int opcion = (int) res[0];
			int num = (int) res[1];
			switch (opcion) {
				case 1:
					setEnemigos(num);
					setEnemigosLabel(getEnemigos());
	                break;
				case 2:
            		limpiarFondo();
            		ponerImagenDeFondo("viewController/Imagenes/stageBack4.png");
					break;
				case 3:
					setVisible(false);
				case 4:
					if(num==1) {
						ponerImagenDeFondo("viewController/Imagenes/tieso.png");
						limpiarFondo();
						ponerGifDeFondo("viewController/Imagenes/invocacionBoss.gif");
						Timer timer = new Timer();
			            timer.schedule(new TimerTask() {
			                @Override
			                public void run() {
			                	ponerImagenDeFondo("viewController/Imagenes/stageBack4.png");
			                	limpiarFondo();
			                    ponerGifDeFondo("viewController/Imagenes/dormir.gif");
			                    timer.cancel();
			                }
			            }, 4500);
			            break;	
					} else if (num==2) {
						limpiarFondo();
						ponerGifDeFondo("viewController/Imagenes/despertar.gif");
						Timer timer = new Timer();
			            timer.schedule(new TimerTask() {
			                @Override
			                public void run() {
			                    limpiarFondo();
			                    ponerGifDeFondo("viewController/Imagenes/tieso.gif");
			                    timer.cancel();
			                }
			            }, 2500);
			            break;
					} else if (num==3) {
						limpiarFondo();
						ponerGifDeFondo("viewController/imagenes/daño.gif");
						Timer timer = new Timer();
			            timer.schedule(new TimerTask() {
			                @Override
			                public void run() {
			                	ponerImagenDeFondo("viewController/imagenes/tieso.png");
			                    limpiarFondo();
			                    ponerGifDeFondo("viewController/imagenes/invocacionBoss.gif");
			                    timer.cancel();
			                }
			            }, 2000);
			            break;
		            } else if (num==4) {
						limpiarFondo();
						ponerGifDeFondo("viewController/imagenes/muerto.gif");
		            } else if (num==5) {
						limpiarFondo();
						ponerGifDeFondo("viewController/imagenes/luser.gif");
		            }
									
					
			}
		}
	}
    
    
    
	//CONTROLADOR
	private Controlador getControlador() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

	private class Controlador implements ActionListener,KeyListener {
		
		public void handleKeyPressed(int pKey) {
			if (pKey==KeyEvent.VK_UP || pKey==KeyEvent.VK_W) {
				Tablero.getTablero().moverBomberman('u');
	        }
			else if (pKey==KeyEvent.VK_DOWN || pKey==KeyEvent.VK_S) {
				Tablero.getTablero().moverBomberman('d');
	        }
			else if (pKey==KeyEvent.VK_LEFT || pKey==KeyEvent.VK_A) {
				Tablero.getTablero().moverBomberman('l');
	        }
			else if (pKey==KeyEvent.VK_RIGHT || pKey==KeyEvent.VK_D) {
				Tablero.getTablero().moverBomberman('r');
	        }
			else if (pKey==KeyEvent.VK_SPACE) {
				Tablero.getTablero().colocarBomba();
	        }
			
	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			Tablero.getTablero().initializeTablero();
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			getControlador().handleKeyPressed(key);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
