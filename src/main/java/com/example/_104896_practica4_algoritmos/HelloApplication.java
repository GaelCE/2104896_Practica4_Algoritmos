package com.example._104896_practica4_algoritmos;

import controlador.Controlador;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vista.PantallaPrincipal;

public class HelloApplication extends Application{

    @Override
    public void start(Stage stage){
        Controlador controlador=new Controlador();
        PantallaPrincipal pantallaPrincipal=new PantallaPrincipal(controlador);

        stage.setTitle("Eight Off");
        stage.setScene(new Scene(pantallaPrincipal,1500,843));
        stage.show();
    }
}
