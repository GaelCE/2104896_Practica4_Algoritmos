package modelo;

public class Movimiento{
    private Carta carta;
    private TipoZona zonaOrigen;
    private int indiceOrigen;
    private TipoZona zonaDestino;
    private int indiceDestino;

    public Movimiento(Carta carta,TipoZona zonaOrigen,int indiceOrigen,TipoZona zonaDestino,int indiceDestino){
        this.carta=carta;
        this.zonaOrigen=zonaOrigen;
        this.indiceOrigen=indiceOrigen;
        this.zonaDestino=zonaDestino;
        this.indiceDestino=indiceDestino;
    }

    public Carta getCarta(){
        return carta;
    }

    public TipoZona getZonaOrigen(){
        return zonaOrigen;
    }

    public int getIndiceOrigen(){
        return indiceOrigen;
    }

    public TipoZona getZonaDestino(){
        return zonaDestino;
    }

    public int getIndiceDestino(){
        return indiceDestino;
    }
}