package vista;

import controlador.Controlador;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class PantallaPrincipal{
    private static final double RESX=1920.0;
    private static final double RESGAEL=1080.0;
    private AnchorPane paneSolitaire;
    private Controlador controlador;
    private ImageButton btnPista;
    private ImageButton btnDeshacer;
    private ImageButton btnNuevoJuego;

    public PantallaPrincipal(){
        paneSolitaire=new AnchorPane();
        controlador=new Controlador();
    }

    private void construirPantalla(){
        paneSolitaire.getChildren().clear();

        ImageView background=new ImageView(new Image(getClass().getResourceAsStream("/recursos/soliatireBackground.png")));
        background.setPreserveRatio(false);
        background.fitWidthProperty().bind(paneSolitaire.widthProperty());
        background.fitHeightProperty().bind(paneSolitaire.heightProperty());

    }

    private void llenarZona(ZonaView zona){
        int cantidad=controlador.getCantidadCartas(zona.getTipo(),zona.getIndice());
        for(int i=0;i<cantidad;i++){
            zona.agregarCarta(controlador.getCarta(zona.getTipo(),zona.getIndice(),i));
        }
    }



    private void posicionarEnPane(Node nodo, double porcentajeX, double porcentajeY) {
        nodo.translateXProperty().unbind();
        nodo.translateYProperty().unbind();
        nodo.translateXProperty().bind(paneSolitaire.widthProperty().multiply(porcentajeX));
        nodo.translateYProperty().bind(paneSolitaire.heightProperty().multiply(porcentajeY));
    }
}