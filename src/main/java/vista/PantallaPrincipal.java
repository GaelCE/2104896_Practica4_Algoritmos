package vista;

import controlador.Controlador;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import modelo.Movimiento;
import modelo.Palo;
import modelo.TipoZona;

public class PantallaPrincipal extends AnchorPane{
    private static final double RESX=1920.0;
    private static final double RESGAEL=1080.0;
    private static final int NUM_COLUMNAS=8;
    private static final int NUM_CELDAS=8;
    private static final int NUM_FUNDACIONES=4;
    private static final double DESPLAZAMIENTO_COLUMNA=30;
    private static final double X_COLUMNAS=205;
    private static final double SEPARACION_COLUMNAS=200;
    private static final double Y_COLUMNAS=280;
    private static final double X_CELDAS=130;
    private static final double SEPARACION_CELDAS=140;
    private static final double X_FUNDACIONES=1340;
    private static final double SEPARACION_FUNDACIONES=135;
    private static final double Y_ZONAS_SUPERIORES=40;
    private Controlador controlador;
    private ZonaView[] columnas;
    private ZonaView[] celdas;
    private ZonaView[] tableaus;
    private ImageButton btnPista;
    private ImageButton btnDeshacer;
    private ImageButton btnNuevoJuego;

    public PantallaPrincipal(Controlador controlador){
        this.controlador=controlador;
        construirPantalla();
        actualizarPantalla();
    }

    private void construirPantalla(){
        getChildren().clear();

        ImageView fondo=new ImageView(new Image(getClass().getResourceAsStream("/recursos/solitaireBackground.png")));
        fondo.setPreserveRatio(false);
        fondo.fitWidthProperty().bind(widthProperty());
        fondo.fitHeightProperty().bind(heightProperty());
        getChildren().add(fondo);

        columnas=new ZonaView[NUM_COLUMNAS];
        for (int i=0;i<NUM_COLUMNAS;i++){
            columnas[i]=crearZona(TipoZona.COLUMNA,i,DESPLAZAMIENTO_COLUMNA);
            posicionarEnPane(columnas[i],(X_COLUMNAS+i*SEPARACION_COLUMNAS)/RESX,Y_COLUMNAS/RESGAEL);
        }

        celdas=new ZonaView[NUM_CELDAS];
        for (int i=0;i<NUM_CELDAS;i++){
            celdas[i]=crearZona(TipoZona.CELDA,i,0);
            posicionarEnPane(celdas[i],(X_CELDAS+i*SEPARACION_CELDAS)/RESX,Y_ZONAS_SUPERIORES/RESGAEL);
        }

        tableaus =new ZonaView[NUM_FUNDACIONES];
        for (int i=0;i<NUM_FUNDACIONES;i++){
            double x=(X_FUNDACIONES+i*SEPARACION_FUNDACIONES)/RESX;
            double y=Y_ZONAS_SUPERIORES/RESGAEL;

            Label lbPalo=new Label(Palo.values()[i].getFigura());
            lbPalo.setPrefSize(ZonaView.ANCHO_CARTA,ZonaView.ALTO_CARTA);
            lbPalo.setAlignment(Pos.CENTER);
            lbPalo.setStyle("-fx-text-fill:#c8a96e;-fx-font-size:60px;");
            getChildren().add(lbPalo);
            posicionarEnPane(lbPalo,x,y);

            tableaus[i]=crearZona(TipoZona.FUNDACION,i,0);
            posicionarEnPane(tableaus[i],x,y);
        }

        btnPista=new ImageButton("/recursos/btnPista.png","/recursos/btnPistaHover.png",200,80);
        btnPista.setOnAction(e->{
            controlador.pedirPista();
            actualizarPantalla();
        });

        btnDeshacer=new ImageButton("/recursos/boton_undo.png","/recursos/boton_undo_hover.png",100,100);
        btnDeshacer.setOnAction(e->{
            controlador.deshacer();
            actualizarPantalla();
        });

        btnNuevoJuego=new ImageButton("/recursos/btonNuevoHover.png","/recursos/btonNuevo.png",200,80);
        btnNuevoJuego.setOnAction(e->nuevoJuego());

        getChildren().addAll(btnPista,btnDeshacer,btnNuevoJuego);
        posicionarEnPane(btnPista,600/RESX,960/RESGAEL);
        posicionarEnPane(btnDeshacer,1160/RESX,960/RESGAEL);
        posicionarEnPane(btnNuevoJuego,860/RESX,960/RESGAEL);
    }

    private ZonaView crearZona(TipoZona tipo,int indice,double desplazamiento){
        ZonaView zona=new ZonaView(tipo,indice,desplazamiento);
        zona.setOnMouseClicked(e->{
            controlador.seleccionar(tipo,indice);
            actualizarPantalla();
        });
        getChildren().add(zona);
        return zona;
    }

    private void actualizarPantalla(){
        for (int i=0;i<NUM_COLUMNAS;i++){
            columnas[i].limpiar();
            llenarZona(columnas[i]);
        }
        for (int i=0;i<NUM_CELDAS;i++){
            celdas[i].limpiar();
            llenarZona(celdas[i]);
        }
        for (int i=0;i<NUM_FUNDACIONES;i++){
            tableaus[i].limpiar();
            llenarZona(tableaus[i]);
        }

        if (controlador.haySeleccion()){
            obtenerZona(controlador.getZonaSeleccionada(),controlador.getIndiceSeleccionado()).resaltar(true);
        }
        mostrarPista();
        btnDeshacer.setDisable(!controlador.puedeDeshacer());

        if (controlador.estaGanado()||controlador.estaSinMovimientos()){
            mostrarFinJuego();
        }
    }

    private void llenarZona(ZonaView zona){
        int cantidad=controlador.getCantidadCartas(zona.getTipo(),zona.getIndice());
        for (int i=0;i<cantidad;i++){
            zona.agregarCarta(controlador.getCarta(zona.getTipo(),zona.getIndice(),i));
        }
    }

    private ZonaView obtenerZona(TipoZona tipo,int indice){
        if (tipo==TipoZona.COLUMNA){
            return columnas[indice];
        }
        if (tipo==TipoZona.CELDA){
            return celdas[indice];
        }
        return tableaus[indice];
    }

    private void mostrarPista(){
        Movimiento pista=controlador.getPista();
        if (pista==null){
            return;
        }
        obtenerZona(pista.getZonaOrigen(),pista.getIndiceOrigen()).resaltar(true);
        obtenerZona(pista.getZonaDestino(),pista.getIndiceDestino()).resaltar(true);
    }

    private void mostrarFinJuego(){
        VBox panel=new VBox(20);
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color:rgba(0,0,0,0.85);");
        panel.prefWidthProperty().bind(widthProperty());
        panel.prefHeightProperty().bind(heightProperty());

        Label lbMensaje=new Label();
        if (controlador.estaGanado()){
            lbMensaje.setText("¡Ganaste!");
        } else {
            lbMensaje.setText("Ya no hay más movimientos");
        }
        lbMensaje.setStyle("-fx-text-fill:#f5d97a;-fx-font-size:36px;-fx-font-weight:bold;");

        Button btnOtraPartida=new Button("Nuevo juego");
        btnOtraPartida.setOnAction(e->nuevoJuego());

        panel.getChildren().addAll(lbMensaje,btnOtraPartida);
        getChildren().add(panel);
    }

    private void nuevoJuego(){
        controlador.nuevoJuego();
        construirPantalla();
        actualizarPantalla();
    }

    private void posicionarEnPane(Node nodo,double porcentajeX,double porcentajeY){
        nodo.translateXProperty().unbind();
        nodo.translateYProperty().unbind();
        nodo.translateXProperty().bind(widthProperty().multiply(porcentajeX));
        nodo.translateYProperty().bind(heightProperty().multiply(porcentajeY));
    }
}