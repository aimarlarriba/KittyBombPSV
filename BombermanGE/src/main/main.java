package main;

import model.Tableros.Seleccion;
import viewController.Pantallas.PantallaInicio;

public class main {
    public static void main(String[] args) {
    	//MODELO//
    	Seleccion.getSeleccion();
    	//VISTA//
    	@SuppressWarnings("unused")
    	PantallaInicio pantalla = new PantallaInicio();
    }
}
