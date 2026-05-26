package viewController.Pantallas;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.Tableros.SeleccionTablero;

@SuppressWarnings({"deprecation"})
public class PantallaSeleccionTipoTablero extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private Image backgroundImage;
	private Controlador controlador;
	private JPanel contentPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private Map<String, Component> panelMap = new HashMap<>();
	
	public PantallaSeleccionTipoTablero() {
		initialize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SeleccionTablero.getSeleccionTablero().addObserver(this);
        getControlador().actionPerformed(null);
	}
	private void initialize() {
		// TODO Auto-generated method stub
		setBounds(100, 100, 707, 438);
        setResizable(false);
        setLocationRelativeTo(null);
        addKeyListener(getControlador());
        setFocusable(true);
        requestFocus();
        setContentPane(getContentPane());
        setVisible(true);
	}
	
	public JPanel getContentPane() {
        try {
            backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("viewController/Imagenes/seleccion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        contentPane = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        contentPane.add(getPanel(19,1));
        contentPane.add(getPanel(185,2));
        contentPane.add(getPanel(355,3));
        contentPane.add(getPanel(525,4));
        panelMap.put("panel_1", panel_1);
        panelMap.put("panel_2", panel_2);
        panelMap.put("panel_3", panel_3);
        panelMap.put("panel_4", panel_4);
        contentPane.revalidate();
        contentPane.repaint();
		return contentPane;
	}
	
	private JPanel getPanel(int pX, int pTablero) {
	    JPanel panel = new JPanel(null);
	    panel.setBounds(pX, 90, 152, 270);

	    JLabel label = crearImagen(pTablero, 152, 270);
	    label.setBounds(0, 0, 152, 270);
	    panel.add(label);
	    panel.setOpaque(false);
	    switch (pTablero) {
	        case 1: panel_1 = panel; break;
	        case 2: panel_2 = panel; break;
	        case 3: panel_3 = panel; break;
	        case 4: panel_4 = panel; break;
	    }

	    return panel;
	}
	
	private JLabel crearImagen(int pTablero, int width, int height) {
		try {
	        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("viewController/Imagenes/" + pTablero + ".png"));
	        Image image = icon.getImage();
	        if (image.getWidth(null) != width || image.getHeight(null) != height) {
	            image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); 
	        }
	        return new JLabel(new ImageIcon(image));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new JLabel();
	    }
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof SeleccionTablero) {
			int seleccionPantalla = (int) arg;
			switch (seleccionPantalla) {
				case 0:
					this.setVisible(false);
					break;
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
				if (pKey==KeyEvent.VK_ENTER || pKey==KeyEvent.VK_SPACE) {
					SeleccionTablero.getSeleccionTablero().cerrarPantalla();
				} else if(pKey==KeyEvent.VK_1) {
					SeleccionTablero.getSeleccionTablero().tableroSelecciondado(1);
				}else if(pKey==KeyEvent.VK_2) {
					SeleccionTablero.getSeleccionTablero().tableroSelecciondado(2);
				}else if(pKey==KeyEvent.VK_3) {
					SeleccionTablero.getSeleccionTablero().tableroSelecciondado(3);
				}else if(pKey==KeyEvent.VK_4) {
					SeleccionTablero.getSeleccionTablero().tableroSelecciondado(4);
				}
			}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			int key = e.getKeyCode();
			getControlador().handleKeyPressed(key);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			}
	    }
	
}
