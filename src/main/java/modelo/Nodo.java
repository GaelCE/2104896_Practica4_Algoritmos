package modelo;

public class Nodo <T>{
    private T info;
    private Nodo<T> sig;

    public Nodo (T dato){
        info=dato;
    }

    public void setInfo(T dato){
        info=dato;
    }

    public T getInfo(){
        return info;
    }

    public void setSig(Nodo<T> sig) {
        this.sig = sig;
    }

    public Nodo<T> getSig(){
        return sig;
    }
}
