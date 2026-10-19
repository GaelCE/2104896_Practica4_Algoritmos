package modelo;

/**
 * Carta de baraja inglesa.
 *
 * @author (Cecilia Curlango Rosas)
 * @version (2025-2)
 */
public class Carta implements Comparable<Carta> {
    private int valor;
    private Palo palo;
    private String color;
    private boolean faceup;

    /**
     * Crea una carta que no se ve su contenido.
     * @param valor valor de la carta (2-14, 15 = Joker)
     * @param palo palo de la carta
     * @param color color de la carta
     */
    public Carta(int valor,Palo palo,String color){
        this.valor=valor;
        this.palo=palo;
        this.color=color;
        faceup=false;
    }

    /**
     * Voltea una carta para que NO se vea su contenido.
     */
    public void makeFaceDown(){
        faceup=false;
    }

    /**
     * Voltea una carta para que se vea su contenido.
     */
    public void makeFaceUp(){
        faceup=true;
    }

    /**
     * Indica si se ve la cara de la carta
     * @return true si se ve, false si está volteada
     */
    public boolean isFaceup(){
        return faceup;
    }

    @Override
    public String toString(){
        if(!faceup){
            return "\uD83C\uDCA0";
        }
        return switch(valor){
            case 1 -> "A"+palo.getFigura();
            case 11 -> "J"+palo.getFigura();
            case 12 -> "Q"+palo.getFigura();
            case 13 -> "K"+palo.getFigura();
            case 15 -> "Joker";
            default -> ""+valor+palo.getFigura();
        };
    }

    public boolean tieneElMismoValor(Carta carta){
        return valor==carta.valor;
    }

    public boolean tieneElMismoPalo(Palo palo){
        return this.palo.equals(palo);
    }

    /**
     * Indica si esta carta se puede colocar sobre la carta recibida en una
     * columna: debe ser del mismo palo y tener un valor exactamente menor.
     * @param carta carta que quedaría debajo
     * @return true si se puede colocar sobre ella
     */
    public boolean puedeIrSobre(Carta carta){
        return palo.equals(carta.palo)&&valor==carta.valor-1;
    }

    /**
     * Indica si esta carta va después de la carta recibida en una fundación:
     * debe ser del mismo palo y tener un valor exactamente mayor.
     * @param carta carta que está actualmente arriba de la fundación
     * @return true si es la siguiente en la fundación
     */
    public boolean esSiguienteEnPalo(Carta carta){
        return palo.equals(carta.palo)&&valor==carta.valor+1;
    }

    public boolean esRey(){
        return valor==13;
    }

    public boolean esAs(){
        return valor==1;
    }


    /**
     * Compara primero por valorBajo (el As es la carta más baja) y, si es
     * igual, por el peso del palo.
     */
    @Override
    public int compareTo(Carta o){
        if(valor!=o.valor){
            return Integer.compare(valor,o.valor);
        }
        return Integer.compare(palo.getPeso(),o.palo.getPeso());
    }

    public int getValor(){
        return valor;
    }

    public Palo getPalo(){
        return palo;
    }

    public String getColor(){
        return color;
    }
}