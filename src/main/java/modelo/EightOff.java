package modelo;

public class EightOff{
    private static final int NUM_COLUMNAS=8;
    private static final int NUM_CELDAS=8;
    private static final int NUM_FUNDACIONES=4;
    private static final int CARTAS_POR_COLUMNA=6;
    private Mazo mazo;
    private ListaSimple<Carta> columnas;
    private ListaSimple<Carta> celdas;
    private ListaSimple<Carta> fundaciones;
    private ListaSimple<Movimiento> historial;

    public EightOff(){
    }

    private void repartir(){
    }

    public boolean puedeMover(TipoZona zonaOrigen,int indiceOrigen,TipoZona zonaDestino,int indiceDestino){
        return false;
    }

    public boolean mover(TipoZona zonaOrigen,int indiceOrigen,TipoZona zonaDestino,int indiceDestino){
        return false;
    }

    public void deshacer(){
    }

    public Movimiento obtenerPista(){
        return null;
    }

    public boolean hayMovimientos(){
        return false;
    }

    public boolean Gano(){
        return false;
    }

    public boolean SinMovimientos(){
        return false;
    }

    public boolean puedeDeshacer(){
        return false;
    }

    public int getCantidadCartas(TipoZona zona,int indice){
        return 0;
    }

    public Carta getCarta(TipoZona zona,int indice,int posicion){
        return null;
    }
}