package modelo;
import java.util.ArrayList;
import java.util.Collections;

public class ListaDobleCircular<T extends Comparable<T>>{
    private NodoDoble<T>inicio;
    private NodoDoble<T> fin;

    public void insertaInicio(T dato){
        NodoDoble<T> n=new NodoDoble<>(dato);
        if (inicio==null){
            inicio=n;
            fin=inicio;
            n.setSig(n);
            n.setAnt(n);
        } else {
            n.setAnt(fin);
            n.setSig(inicio);
            inicio.setAnt(n);
            fin.setSig(n);
            inicio=n;
        }
    }

    public T eliminarInicio(){
        if (inicio==null){
            System.out.println("Lista vacia");
            return null;
        } else {
            T dato=inicio.getInfo();
            if (inicio==fin){
                inicio=null;
                fin=inicio;
            } else {
                inicio=inicio.getSig();
                inicio.setAnt(fin);
                fin.setSig(inicio);
            }
            return dato;
        }
    }

    public void insertaFinal(T dato){
        NodoDoble<T>n=new NodoDoble<>(dato);
        if (inicio==null){
            inicio=n;
            fin=inicio;
            n.setSig(n);
            n.setAnt(n);
        } else {
            n.setSig(inicio);
            n.setAnt(fin);
            fin.setSig(n);
            inicio.setAnt(n);
            fin=n;
        }
    }

    public T eliminarFinal(){
        if (inicio==null){
            System.out.println("lista vacia");
            return null;
        } else {
            T dato=fin.getInfo();
            if (inicio==fin){
                inicio=null;
                fin= inicio;
            } else {
                fin=fin.getAnt();
                fin.setSig(inicio);
                inicio.setAnt(fin);
            }
            return dato;
        }
    }

    public String mostrarLista(){
        if (inicio==null){
            return "Lista vacia";
        } else {
            NodoDoble<T>r=inicio;
            String cadena="";
            do {
                cadena+=r.getInfo()+"\n";
                r=r.getSig();
            }while (r!=inicio);
            return cadena;
        }
    }

    public String mostrarRecursivo(){
        if (inicio==null){
            return "Lista vacia";
        }
        return mostrarRecursivo(inicio);
    }

    public String mostrarRecursivo(NodoDoble<T> n){
        if (n==fin){
            return n.getInfo()+"";
        }
        return n.getInfo()+"\n"+mostrarRecursivo(n.getSig());
    }

    public T eliminarPosicion(int posicion){
        int contador=0;
        NodoDoble<T>n=inicio;
        do {
            if (contador==posicion){
                if (n==inicio){
                    return eliminarInicio();
                } else if (n==fin) {
                    return eliminarFinal();
                } else {
                    n.getSig().setAnt(n.getAnt());
                    n.getAnt().setSig(n.getSig());
                    return n.getInfo();
                }
            }
            contador++;
            n=n.getSig();
        }while (n!=inicio);
        return eliminarFinal();
    }

    public void ordenar(){
        if (inicio==null){
            return;
        }
        ArrayList<T>auxiliar=new ArrayList<>();
        NodoDoble<T>n=inicio;
        do {
            auxiliar.add(n.getInfo());
            n=n.getSig();
        }while (n!=inicio);
        Collections.sort(auxiliar);
        for(int i=0;i<auxiliar.size();i++){
            n.setInfo(auxiliar.get(i));
            n=n.getSig();
        }
    }

    public void mezclar(){
        if (inicio==null){
            return;
        }
        ArrayList<T>auxiliar=new ArrayList<>();
        NodoDoble<T>n=inicio;
        do {
            auxiliar.add(n.getInfo());
            n=n.getSig();
        }while (n!=inicio);
        Collections.shuffle(auxiliar);
        for(int i=0;i<auxiliar.size();i++){
            n.setInfo(auxiliar.get(i));
            n=n.getSig();
        }
    }

    public void InsertaEnPosicion(T dato,int posicion){
        if (inicio==null){
            return;
        } else {
            int contador=0;
            NodoDoble<T>n=inicio;
            do {
                if (contador==posicion){
                    if (n==inicio){
                        insertaInicio(dato);
                    } else if (n==fin) {
                        insertaFinal(dato);
                    } else {
                        NodoDoble<T> nuevo=new NodoDoble<>(dato);
                        nuevo.setSig(n.getSig());
                        nuevo.setAnt(n);
                        n.getSig().setAnt(nuevo);
                        n.setSig(nuevo);
                    }
                }
                n=n.getSig();
                contador++;
            }while (n!=inicio);
        }
    }

    public boolean vacia(){
        return inicio==null;
    }
}
