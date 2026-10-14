package modelo;

/**
 * Nodo generico de un arbol binario.
 * Guarda un dato y tiene como maximo dos hijos: izquierdo y derecho.
 *
 * @param <T> tipo de dato que guarda el nodo
 */
public class Nodo<T> {

    private T dato;
    private Nodo<T> izquierdo;
    private Nodo<T> derecho;

    public Nodo(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    /** Un nodo es hoja cuando no tiene ningun hijo. */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    /** El grado es el numero de hijos del nodo (0, 1 o 2). */
    public int grado() {
        int hijos = 0;
        if (izquierdo != null) {
            hijos++;
        }
        if (derecho != null) {
            hijos++;
        }
        return hijos;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo<T> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo<T> derecho) {
        this.derecho = derecho;
    }

    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}
