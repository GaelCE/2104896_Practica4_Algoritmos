package vista;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Carta;

public class CartaImage extends Label{
    private static final Image[] IMAGENES=new Image[52];
    private Carta carta;

    public CartaImage(Carta carta){
        this.carta=carta;
        ImageView view=new ImageView(obtenerImagen(carta));
        view.setFitWidth(ZonaView.ANCHO_CARTA);
        view.setFitHeight(ZonaView.ALTO_CARTA);
        setGraphic(view);
    }

    private static Image obtenerImagen(Carta carta){
        int clave=(carta.getPalo().getPeso()-1)*13+(carta.getValor()-1);
        if (IMAGENES[clave]==null){
            String ruta="/cartas/"+obtenerValor(carta)+"_of_"+obtenerPalo(carta)+".png";
            IMAGENES[clave]=new Image(CartaImage.class.getResourceAsStream(ruta));
        }
        return IMAGENES[clave];
    }

    private static String obtenerValor(Carta carta){
        switch (carta.getValor()){
            case 1:
                return "ace";
            case 11:
                return "jack";
            case 12:
                return "queen";
            case 13:
                return "king";
            default:
                return ""+carta.getValor();
        }
    }

    private static String obtenerPalo(Carta carta){
        switch (carta.getPalo()){
            case PICA:
                return "spades";
            case TREBOL:
                return "clubs";
            case CORAZON:
                return "hearts";
            default:
                return "diamonds";
        }
    }

    public Carta getCarta(){
        return carta;
    }
}