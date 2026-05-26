package viewController.Pantallas;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import model.Tableros.Seleccion;

@SuppressWarnings({"deprecation"})
public class PantallaInicio extends JFrame implements Observer{

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel;
    private JPanel panel_1;
    private JPanel panel_2;
    private JTextArea txt;
    private Controlador controlador;
    private Map<String, Component> panelMap = new HashMap<>();
    private boolean notificationsEnabled=true;
    private int pantallaSelec=1;
    
    
    public PantallaInicio() {
    	initialize();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Seleccion.getSeleccion().addObserver(this);   
        
    }
    private void initialize() {
    	setBounds(100, 100, 840, 520);
        setResizable(false);
        setLocationRelativeTo(null);
        addKeyListener(getControlador());
        setFocusable(true);
        requestFocus();
        setContentPane(getContentPane());
        setVisible(true);
        }

        
    public JPanel getContentPane() {
    	if (contentPane == null) {
            contentPane = new JPanel(null) {
                private static final long serialVersionUID = 1L;

                private final ImageIcon gifFondo = new ImageIcon(getClass().getClassLoader().getResource("viewController/Imagenes/back.gif"));

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Image img = gifFondo.getImage();
                    if (img != null) {
                        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            };
        
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        /////////////////////////////
        contentPane.add(getPanel_Bomberman(20));
        contentPane.add(getPanel_Bomberman(10));
        contentPane.add(getPanel());
        contentPane.add(getTxt());
        panelMap.put("panel_1", panel_1);
        panelMap.put("panel_2", panel_2);
        panelMap.put("panel", panel);
        clearPane(10);
        clearPane(20);
        contentPane.setComponentZOrder((JPanel) panelMap.get("panel"), 2);
        contentPane.setComponentZOrder((JPanel) panelMap.get("panel_1"), 1);
        contentPane.setComponentZOrder((JPanel) panelMap.get("panel_2"), 0);
        contentPane.revalidate();
        contentPane.repaint();
        return contentPane;
        }
		return contentPane;
    }
    
    private JPanel getPanel_Bomberman(int BMSelec) {
    	int BM = BMSelec;
    	if (BMSelec==1||BMSelec==10) {
    		if (panel_1 == null) {
                panel_1 = new JPanel();
                panel_1.setOpaque(false);
                panel_1.setLayout(null);
                JLabel label;
                panel_1.setBounds(400, 155, 350, 327);
                label = crearImagen(BM,300, 327);
                if (label!=null) {
                	panel_1.add(label);
                }
                
            }
            return panel_1;
    	} else if(BMSelec==2||BMSelec==20) {
    		if (panel_2 == null) {
                panel_2 = new JPanel();
                panel_2.setOpaque(false);
                panel_2.setLayout(null);
                panel_2.setBounds(154, 155, 238, 327);
                JLabel label = crearImagen(BM,238, 327);
                panel_2.add(label);
            }
            return panel_2;
    	}
		return null;
        
    }
    
    private JLabel crearImagen(int BM, int dir1, int dir2) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("viewController/Imagenes/bomber"+BM+".png"));
        Image image = icon.getImage().getScaledInstance(dir1, dir2, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(0, 0, dir1, dir2);
        return label;
    }

    private JPanel getPanel() {
        if (panel == null) {
            panel = new JPanel();
            panel.setOpaque(false);
            panel.setLayout(null);
            panel.setBounds(117, 20, 576, 160);
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("viewController/Imagenes/title.png"));
            Image image = icon.getImage().getScaledInstance(576, 160, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(image));
            label.setBounds(0, 0, 576, 160);
            panel.add(label);
            
        }
        return panel;
    }
    
	private JTextArea getTxt() {
		if (txt == null) {
			txt = new JTextArea();
	        txt.setText("Selecciona con ← y →\n Presiona <o>pciones");
	        txt.setForeground(Color.MAGENTA);
	        txt.setEditable(false); 
	        txt.setOpaque(false);
	        txt.setFocusable(false);
	        txt.setBorder(null);
	        txt.setFont(new Font("SansSerif", Font.PLAIN, 12));
	        txt.setBounds(686, 428, 160, 50); 
	        txt.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
	        txt.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
		}
		return txt;
	}
	
	public void reiniciarJuego() {
		revalidate();
		repaint();
		setVisible(true);
	}
	
	private void setPane(int BMSelec) {
		JPanel panel = (JPanel) panelMap.get("panel_" + BMSelec);
		if(panel!=null) {
			for (Component comp : panel.getComponents()) {
			    if (comp instanceof JLabel) {
			        JLabel label = (JLabel) comp;
			        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("viewController/Imagenes/bomber" + BMSelec + ".png"));
			        Image image = icon.getImage().getScaledInstance(238, 327, Image.SCALE_SMOOTH);
			        label.setIcon(new ImageIcon(image));
			        break;
			    }
			}
			ImageIcon originalGif = new ImageIcon(getClass().getClassLoader().getResource("viewController/Imagenes/gif.gif"));
			Image scaledGif = originalGif.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
			ImageIcon gifIcon = new ImageIcon(scaledGif);
			JLabel gifLabel = new JLabel(gifIcon);
	        gifLabel.setName("gif");
	        int gifWidth = gifIcon.getIconWidth();
	        int gifHeight = gifIcon.getIconHeight();
	        int x = (238 - gifWidth) / 2;
	        int y = 50 ;
	        gifLabel.setBounds(x, y, gifWidth, gifHeight);
	        panel.add(gifLabel);
	        panel.setComponentZOrder(gifLabel, panel.getComponentCount() - 1);
		}
		contentPane.revalidate();
        contentPane.repaint();
	}
	
	private void clearPane(int BMSelec) {
		JPanel panel = (JPanel) panelMap.get("panel_" + (BMSelec)/10);
		if(panel!=null) {
			for (Component comp : panel.getComponents()) {
			    if (comp instanceof JLabel) {
			        JLabel label = (JLabel) comp;
			        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("viewController/Imagenes/bomber" + BMSelec + ".png"));
			        Image image = icon.getImage().getScaledInstance(180, 246, Image.SCALE_SMOOTH);
			        label.setIcon(new ImageIcon(image));
			        break;
			    }
			}
			 for (Component comp : panel.getComponents()) {
		            if (comp.getName() != null && comp.getName().equals("gif")) {
		                panel.remove(comp);
		                
		                break;
		            }
	        }
		}
		contentPane.revalidate();
        contentPane.repaint();
	}	

	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof Seleccion) {
			Object res[]=(Object[])arg;
			int BMSelec = (int) res[0];
			boolean reiniciar= (boolean) res[1];
			if (reiniciar) {
				clearPane((BMSelec+1)*10);
				setPane(BMSelec+1); 
	        	clearPane((BMSelec)*10);
	        	reiniciarJuego();
				}
			else {
				if (notificationsEnabled) {
					switch (BMSelec) {
						case 0:
							setVisible(false);
							@SuppressWarnings("unused")
					    	Pantalla pantalla0 = new Pantalla(1,pantallaSelec);
				        case 1:
				        	setPane(BMSelec+1); 
				        	clearPane((BMSelec)*10);
				        	break;
				        case 2:
				        	setPane(BMSelec-1);
				        	clearPane((BMSelec)*10);
				        	break;
				        case 3:
				        	setVisible(false);
				        	@SuppressWarnings("unused")
					    	Pantalla pantalla1 = new Pantalla(2,pantallaSelec);
				        	break;
				        case 4:
				        	setEnabled(false);
				        	notificationsEnabled = false;
				        	@SuppressWarnings("unused")
					    	PantallaSeleccionTipoTablero pantalla2 = new PantallaSeleccionTipoTablero();
				        	break;
				        default:
					}
				} else {
					switch (BMSelec) {
						case 5:
							notificationsEnabled = true;
							setEnabled(true);
							setVisible(true);
			        	break;
					}
				}
			}
			switch (BMSelec) {
			case 10:
				pantallaSelec=1;
				break;
	        case 20:
	        	pantallaSelec=2;
	        	break;
	        case 30:
	        	pantallaSelec=3;
	        	break;
	        case 40:
	        	pantallaSelec=4;
	        	break;
	        default:
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
				if (pKey==KeyEvent.VK_LEFT) {
					Seleccion.getSeleccion().seleccionarBomberMan('l');
		        }
				else if (pKey==KeyEvent.VK_RIGHT) {
					Seleccion.getSeleccion().seleccionarBomberMan('r');
		        }
				else if (pKey==KeyEvent.VK_ENTER || pKey==KeyEvent.VK_SPACE) {
					Seleccion.getSeleccion().inicializarTablero();
				}
				else if (pKey==KeyEvent.VK_O) {
					Seleccion.getSeleccion().seleccionarTipoTablero();
				}
		    }

			@Override
			public void actionPerformed(ActionEvent e) {
				
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
