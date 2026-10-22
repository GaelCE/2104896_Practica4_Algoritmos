package controlador;

import modelo.Carta;
import modelo.EightOff;
import modelo.Movimiento;
import modelo.TipoZona;

public class Controlador{
    private EightOff juego;
    private TipoZona zonaSeleccionada;
    private int indiceSeleccionado;
    private Movimiento pista;

    public Controlador(){
        juego=new EightOff();
        limpiarSeleccion();
        pista=null;
    }

    private void limpiarSeleccion(){
        zonaSeleccionada=null;
        indiceSeleccionado=-1;
    }

    public void seleccionar(TipoZona zona,int indice){
        pista=null;
        if (!haySeleccion()){
            if (zona!=TipoZona.TABLEAU&&juego.getCantidadCartas(zona,indice)>0){
                zonaSeleccionada=zona;
                indiceSeleccionado=indice;
            }
            return;
        }
        if (zona==zonaSeleccionada&&indice==indiceSeleccionado){
            limpiarSeleccion();
            return;
        }
        juego.mover(zonaSeleccionada,indiceSeleccionado,zona,indice);
        limpiarSeleccion();
    }

    public void pedirPista(){
        limpiarSeleccion();
        pista=juego.obtenerPista();
    }

    public void deshacer(){
        juego.deshacer();
        limpiarSeleccion();
        pista=null;
    }

    public void nuevoJuego(){
        juego=new EightOff();
        limpiarSeleccion();
        pista=null;
    }

    public boolean haySeleccion(){
        return zonaSeleccionada!=null;
    }

    public TipoZona getZonaSeleccionada(){
        return zonaSeleccionada;
    }

    public int getIndiceSeleccionado(){
        return indiceSeleccionado;
    }

    public Movimiento getPista(){
        return pista;
    }

    public boolean estaGanado(){
        return juego.Gano();
    }

    public boolean estaSinMovimientos(){
        return juego.SinMovimientos();
    }

    public boolean puedeDeshacer(){
        return juego.puedeDeshacer();
    }

    public int getCantidadCartas(TipoZona zona,int indice){
        return juego.getCantidadCartas(zona,indice);
    }

    public Carta getCarta(TipoZona zona,int indice,int posicion){
        return juego.getCarta(zona,indice,posicion);
    }
}