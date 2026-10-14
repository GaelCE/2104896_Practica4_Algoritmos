package modelo;

public class ListaSimple <T>{
    private Nodo<T> inicio;

    public void insertarInicio(T dato){
        Nodo<T> n=new Nodo<>(dato);
        n.setSig(inicio);
        inicio=n;
    }

    public T eliminarInicio(){
        if (inicio==null){
            System.out.println("lista vacia");
            return null;
        } else {
            T dato=inicio.getInfo();
            inicio=inicio.getSig();
            return dato;
        }
    }

    public void insertarFinal(T dato){
        Nodo<T>n=new Nodo<T>((dato));
        if (inicio==null){
            inicio=n;
        } else {
            Nodo<T>r=inicio;
            while (r.getSig()!=null){
                r=r.getSig();
            }
            r.setSig(n);
        }
    }

    public T eliminarFinal(){
        if (inicio==null){
            System.out.println("Lista vacia");
            return null;
        } else {
            if (inicio.getSig()==null){
                T dato=inicio.getInfo();
                inicio=null;
                return dato;
            } else {
                Nodo<T>r=inicio;
                Nodo<T>a=r;
                while (r.getSig()!=null){
                    a=r;
                    r=r.getSig();
                }
                T dato=r.getInfo();
                a.setSig(null);
                return dato;
            }
        }
    }

    public String mostrarLista(){
        if (inicio==null){
            return "Lista vacia";
        } else {
            Nodo<T>r=inicio;
            String cadena="";
            while (r!=null){
                cadena+=r.getInfo()+"\n";
                r=r.getSig();
            }
            return cadena;
        }
    }

    public String mostrarRecursivo(){
        if (inicio==null){
            return "Lista vacia";
        }
        return mostrarRecursivo(inicio);
    }

    public String mostrarRecursivo(Nodo<T> n){
        if (n.getSig()==null){
            return ""+n.getInfo();
        }
        return n.getInfo()+"\n"+mostrarRecursivo(n.getSig());
    }

    public T eliminaX(T objeto){
        if (inicio==null){
            return null;
        }
        Nodo<T> n = inicio;
        Nodo<T> a=n;
        while (n.getSig()!=null){
            if (n.getInfo().equals(objeto)){
                T dato=n.getInfo();
                if (n==inicio){
                    eliminarInicio();
                } else {
                  a.setSig(n.getSig());
                }
                return dato;
            }
            a=n;
            n=n.getSig();
        }
        return null;
    }

    public int buscar(T objeto){
        int posicion=0;
        Nodo<T> n=inicio;
        while (n!=null){
            if (n.getInfo().equals(objeto)){
                return posicion;
            }
            posicion++;
            n=n.getSig();
        }
        return -1;
    }

    public T eliminarPosicion(int posicion){
        int contador=0;
        Nodo<T>n=inicio;
        Nodo<T>a=n;
        while (n!=null){
            if (contador==posicion){
                if (n==inicio){
                    return eliminarInicio();
                } else if (n.getSig()==null) {
                    return eliminarFinal();
                } else {
                    a.setSig(n.getSig());
                    return n.getInfo();
                }
            }
            a=n;
            n=n.getSig();
            contador++;
        }
        return eliminarFinal();
    }
}
