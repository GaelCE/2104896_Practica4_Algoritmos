package modelo;
/**
 * Write a description of class Mazo here.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */
import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    private ListaDobleCircular<Carta> cartas = new ListaDobleCircular<>();

    public Mazo() {
        llenar(); // crea todas las cartas, excluyendo Jokers
        mezclar();
    }

    /**
     * Obtiene todas las cartas del mazo.
     * @return
     */
    public ListaDobleCircular<Carta> getCartas() {
        return cartas;
    }

    public Carta obtenerUnaCarta() {
        if (!cartas.vacia()) {
            return cartas.eliminarFinal();
        }
        return null;
    }
    private void mezclar() {
        cartas.mezclar();
    }

    private void llenar() {
        for (int i = 1; i <=13 ; i++) {
            for (Palo palo : Palo.values()) {
                Carta c = new Carta(i,palo, palo.getColor());
                cartas.insertaFinal(c);
            }
        }
    }

    public void ordenar() {
        cartas.ordenar();
    }

    @Override
    public String toString() {
        return cartas.toString();
    }
}
