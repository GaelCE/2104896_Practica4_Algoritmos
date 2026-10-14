package modelo;

public class NodoDoble<T>{
    private T info;
    private  NodoDoble<T> sig;
    private NodoDoble<T> ant;

    public NodoDoble(T info){
        this.info=info;
    }

    public T getInfo() {
        return info;
    }

    public NodoDoble<T> getAnt() {
        return ant;
    }

    public NodoDoble<T> getSig() {
        return sig;
    }

    public void setSig(NodoDoble<T> sig) {
        this.sig = sig;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public void setAnt(NodoDoble<T> ant) {
        this.ant = ant;
    }
}
