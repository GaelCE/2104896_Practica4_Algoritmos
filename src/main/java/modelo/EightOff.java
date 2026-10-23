package modelo;

public class EightOff{
    private static final int NUM_COLUMNAS=8;
    private static final int NUM_CELDAS=8;
    private static final int NUM_FUNDACIONES =4;
    private static final int CARTAS_POR_COLUMNA=6;
    private static final int CARTAS_POR_PALO=13;
    private Mazo mazo;
    private ListaSimple<Carta>[] columnas;
    private ListaSimple<Carta> celdas;
    private ListaSimple<Carta>[] fundaciones;
    private ListaSimple<Movimiento> historial;

    public EightOff(){
        mazo=new Mazo();
        inicializarArreglos();
        repartir();
    }

    private void repartir(){
        for (int i=0;i<NUM_COLUMNAS;i++){
            for (int j=0;j<CARTAS_POR_COLUMNA;j++){
                Carta carta=mazo.obtenerUnaCarta();
                carta.makeFaceUp();
                columnas[i].insertarFinal(carta);
            }
        }
        for (int i=0;i<4;i++){
            Carta carta=mazo.obtenerUnaCarta();
            carta.makeFaceUp();
            celdas.insertarFinal(carta);
        }
    }

    private Carta obtenerCartaOrigen(TipoZona zona,int indice){
        if (zona==TipoZona.COLUMNA){
            if (columnas[indice].vacia()){
                return null;
            }
            return columnas[indice].verFinal();
        }
        if (zona==TipoZona.CELDA){
            if (indice<celdas.getTamano()){
                return celdas.getElemento(indice);
            }
        }
        return null;
    }

    public boolean puedeMover(TipoZona zonaOrigen,int indiceOrigen,TipoZona zonaDestino,int indiceDestino){
        Carta carta=obtenerCartaOrigen(zonaOrigen,indiceOrigen);
        if (carta==null){
            return false;
        }

        if (zonaDestino==TipoZona.COLUMNA){
            if (zonaOrigen==TipoZona.COLUMNA&&indiceOrigen==indiceDestino){
                return false;
            }
            if (columnas[indiceDestino].vacia()){
                if (zonaOrigen==TipoZona.COLUMNA&&columnas[indiceOrigen].getTamano()==1){
                    return false;
                }
                return carta.esRey();
            }
            return carta.puedeIrSobre(columnas[indiceDestino].verFinal());
        }

        if (zonaDestino==TipoZona.CELDA){
            return zonaOrigen==TipoZona.COLUMNA&&celdas.getTamano()<NUM_CELDAS;
        }

        if (zonaDestino==TipoZona.FUNDACION){
            if (indiceDestino!=carta.getPalo().getPeso()-1){
                return false;
            }
            if (fundaciones[indiceDestino].vacia()){
                return carta.esAs();
            }
            return carta.esSiguienteEnPalo(fundaciones[indiceDestino].verFinal());
        }

        return false;
    }

    public boolean mover(TipoZona zonaOrigen,int indiceOrigen,TipoZona zonaDestino,int indiceDestino){
        if (!puedeMover(zonaOrigen,indiceOrigen,zonaDestino,indiceDestino)){
            return false;
        }

        Carta carta;
        if (zonaOrigen==TipoZona.COLUMNA){
            carta=columnas[indiceOrigen].eliminarFinal();
        } else {
            carta=celdas.eliminarPosicion(indiceOrigen);
        }

        int destino=indiceDestino;
        if (zonaDestino==TipoZona.COLUMNA){
            columnas[destino].insertarFinal(carta);
        } else if (zonaDestino==TipoZona.CELDA){
            destino=celdas.getTamano();
            celdas.insertarFinal(carta);
        } else {
            fundaciones[destino].insertarFinal(carta);
        }

        historial.insertarInicio(new Movimiento(carta,zonaOrigen,indiceOrigen,zonaDestino,destino));
        return true;
    }

    public void deshacer(){
        if (historial.vacia()){
            return;
        }
        Movimiento movimiento=historial.eliminarInicio();

        Carta carta;
        if (movimiento.getZonaDestino()==TipoZona.COLUMNA){
            carta=columnas[movimiento.getIndiceDestino()].eliminarFinal();
        } else if (movimiento.getZonaDestino()==TipoZona.CELDA){
            carta=celdas.eliminarPosicion(movimiento.getIndiceDestino());
        } else {
            carta= fundaciones[movimiento.getIndiceDestino()].eliminarFinal();
        }

        if (movimiento.getZonaOrigen()==TipoZona.COLUMNA){
            columnas[movimiento.getIndiceOrigen()].insertarFinal(carta);
        } else {
            celdas.insertarEnPosicion(carta,movimiento.getIndiceOrigen());
        }
    }

    private Movimiento buscarMovimiento(TipoZona zonaOrigen,TipoZona zonaDestino){
        int cantidadOrigenes;
        if (zonaOrigen==TipoZona.COLUMNA){
            cantidadOrigenes=NUM_COLUMNAS;
        } else {
            cantidadOrigenes=celdas.getTamano();
        }

        int cantidadDestinos;
        if (zonaDestino==TipoZona.COLUMNA){
            cantidadDestinos=NUM_COLUMNAS;
        } else if (zonaDestino==TipoZona.FUNDACION){
            cantidadDestinos= NUM_FUNDACIONES;
        } else {
            cantidadDestinos=1;
        }

        for (int i=0;i<cantidadOrigenes;i++){
            for (int j=0;j<cantidadDestinos;j++){
                if (puedeMover(zonaOrigen,i,zonaDestino,j)){
                    Carta carta=obtenerCartaOrigen(zonaOrigen,i);
                    int destino=j;
                    if (zonaDestino==TipoZona.CELDA){
                        destino=celdas.getTamano();
                    }
                    return new Movimiento(carta,zonaOrigen,i,zonaDestino,destino);
                }
            }
        }
        return null;
    }

    public Movimiento obtenerPista(){
        Movimiento pista=buscarMovimiento(TipoZona.COLUMNA,TipoZona.FUNDACION);
        if (pista==null){
            pista=buscarMovimiento(TipoZona.CELDA,TipoZona.FUNDACION);
        }
        if (pista==null){
            pista=buscarMovimiento(TipoZona.CELDA,TipoZona.COLUMNA);
        }
        if (pista==null){
            pista=buscarMovimiento(TipoZona.COLUMNA,TipoZona.COLUMNA);
        }
        if (pista==null){
            pista=buscarMovimiento(TipoZona.COLUMNA,TipoZona.CELDA);
        }
        return pista;
    }

    public boolean hayMovimientos(){
        return obtenerPista()!=null;
    }

    public boolean Gano(){
        for (int i = 0; i< NUM_FUNDACIONES; i++){
            if (fundaciones[i].getTamano()<CARTAS_POR_PALO){
                return false;
            }
        }
        return true;
    }

    public boolean SinMovimientos(){
        return !Gano()&&!hayMovimientos();
    }

    public boolean puedeDeshacer(){
        return !historial.vacia();
    }

    public int getCantidadCartas(TipoZona zona,int indice){
        if (zona==TipoZona.COLUMNA){
            return columnas[indice].getTamano();
        }
        if (zona==TipoZona.CELDA){
            if (indice<celdas.getTamano()){
                return 1;
            }
            return 0;
        }
        return fundaciones[indice].getTamano();
    }

    public Carta getCarta(TipoZona zona,int indice,int posicion){
        if (zona==TipoZona.COLUMNA){
            return columnas[indice].getElemento(posicion);
        }
        if (zona==TipoZona.CELDA){
            return celdas.getElemento(indice);
        }
        return fundaciones[indice].getElemento(posicion);
    }

    public void inicializarArreglos(){
        columnas=new ListaSimple[NUM_COLUMNAS];
        for(int i=0;i<NUM_COLUMNAS;i++){
            columnas[i]=new ListaSimple<>();
        }
        celdas=new ListaSimple<>();
        fundaciones =new ListaSimple[NUM_FUNDACIONES];
        for (int i = 0; i< NUM_FUNDACIONES; i++){
            fundaciones[i]=new ListaSimple<>();
        }
        historial=new ListaSimple<>();
    }
}