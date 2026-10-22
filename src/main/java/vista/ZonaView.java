package vista;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import modelo.Carta;
import modelo.TipoZona;

public class ZonaView extends AnchorPane{
    public static final double ANCHO_CARTA=100;
    public static final double ALTO_CARTA=120;
    private static final Color COLOR_BORDE=Color.web("#c8a96e",0.6);
    private TipoZona tipo;
    private int indice;
    private double desplazamiento;
    private Rectangle espacio;
    private CartaImage ultimaCarta;
    private int cantidadCartas;

    public ZonaView(TipoZona tipo,int indice,double desplazamiento){
        this.tipo=tipo;
        this.indice=indice;
        this.desplazamiento=desplazamiento;
        espacio=new Rectangle(ANCHO_CARTA,ALTO_CARTA);
        espacio.setArcWidth(12);
        espacio.setArcHeight(12);
        espacio.setFill(Color.TRANSPARENT);
        limpiar();
    }

    public void limpiar(){
        getChildren().clear();
        espacio.setStroke(COLOR_BORDE);
        espacio.setStrokeWidth(2);
        getChildren().add(espacio);
        ultimaCarta=null;
        cantidadCartas=0;
        setPrefSize(ANCHO_CARTA,ALTO_CARTA);
    }

    public void agregarCarta(Carta carta){
        CartaImage imagen=new CartaImage(carta);
        imagen.setLayoutY(cantidadCartas*desplazamiento);
        getChildren().add(imagen);
        ultimaCarta=imagen;
        cantidadCartas++;
        setPrefHeight(ALTO_CARTA+(cantidadCartas-1)*desplazamiento);
    }

    public void resaltar(boolean resaltada){
        if (resaltada){
            if (ultimaCarta!=null){
                ultimaCarta.setEffect(new DropShadow(20,Color.GOLD));
            } else {
                espacio.setStroke(Color.GOLD);
                espacio.setStrokeWidth(4);
            }
        } else {
            if (ultimaCarta!=null){
                ultimaCarta.setEffect(null);
            }
            espacio.setStroke(COLOR_BORDE);
            espacio.setStrokeWidth(2);
        }
    }

    public TipoZona getTipo(){
        return tipo;
    }

    public int getIndice(){
        return indice;
    }
}