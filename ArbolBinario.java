package negocio;

import java.util.Objects;
import java.util.function.Predicate;

import modelo.Nodo;

/**
 * Árbol binario genérico.
 * Los conteos y la altura se calculan con recursividad.
 * No usa TreeSet, TreeMap, PriorityQueue, NavigableSet ni NavigableMap.
 *
 * @param <T> tipo de dato que guardan los nodos
 */
public class ArbolBinario<T> {

    private Nodo<T> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    /** Crea el nodo raíz. Falla si el árbol ya tiene raíz. */
    public Nodo<T> crearRaiz(T dato) {
        if (raiz != null) {
            throw new IllegalStateException("El árbol ya tiene raíz: " + raiz.getDato());
        }
        raiz = new Nodo<>(dato);
        return raiz;
    }

    /** Agrega un hijo izquierdo al padre. Falla si esa posición está ocupada. */
    public Nodo<T> agregarIzquierdo(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getIzquierdo() != null) {
            throw new IllegalStateException("El nodo " + padre.getDato()
                    + " ya tiene hijo izquierdo: " + padre.getIzquierdo().getDato());
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        padre.setIzquierdo(nuevo);
        return nuevo;
    }

    /** Agrega un hijo derecho al padre. Falla si esa posición está ocupada. */
    public Nodo<T> agregarDerecho(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getDerecho() != null) {
            throw new IllegalStateException("El nodo " + padre.getDato()
                    + " ya tiene hijo derecho: " + padre.getDerecho().getDato());
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        padre.setDerecho(nuevo);
        return nuevo;
    }

    private void validarPadre(Nodo<T> padre) {
        if (padre == null) {
            throw new IllegalArgumentException("El nodo padre no existe.");
        }
    }

    public boolean esVacio() {
        return raiz == null;
    }

    /** Total de nodos del árbol. */
    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    /** Total de hojas (nodos sin hijos). */
    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.esHoja()) {
            return 1;
        }
        return contarHojas(nodo.getIzquierdo()) + contarHojas(nodo.getDerecho());
    }

    /**
     * Altura en aristas: camino más largo desde la raíz hasta una hoja.
     * Árbol vacío = -1, árbol con un solo nodo = 0.
     */
    public int altura() {
        return altura(raiz);
    }

    private int altura(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    /** Busca un nodo por su dato (usa equals). Devuelve null si no existe. */
    public Nodo<T> buscar(T dato) {
        return buscar(nodo -> Objects.equals(nodo, dato));
    }

    /** Busca el primer nodo cuyo dato cumpla la condición. Devuelve null si no existe. */
    public Nodo<T> buscar(Predicate<T> condicion) {
        return buscar(raiz, condicion);
    }

    private Nodo<T> buscar(Nodo<T> nodo, Predicate<T> condicion) {
        if (nodo == null) {
            return null;
        }
        if (condicion.test(nodo.getDato())) {
            return nodo;
        }
        Nodo<T> enIzquierdo = buscar(nodo.getIzquierdo(), condicion);
        if (enIzquierdo != null) {
            return enIzquierdo;
        }
        return buscar(nodo.getDerecho(), condicion);
    }

    /**
     * Muestra el árbol en consola, un nodo por línea.
     * [I] = hijo izquierdo, [D] = hijo derecho.
     */
    public void mostrarArbol() {
        if (esVacio()) {
            System.out.println("(árbol vacío)");
            return;
        }
        System.out.println(raiz.getDato());
        mostrarHijos(raiz, "");
    }

    private void mostrarHijos(Nodo<T> nodo, String prefijo) {
        Nodo<T> izq = nodo.getIzquierdo();
        Nodo<T> der = nodo.getDerecho();

        if (izq != null) {
            boolean ultimo = (der == null);
            imprimirRama(izq, prefijo, ultimo, "[I] ");
        }
        if (der != null) {
            imprimirRama(der, prefijo, true, "[D] ");
        }
    }

    private void imprimirRama(Nodo<T> hijo, String prefijo, boolean ultimo, String etiqueta) {
        System.out.println(prefijo + (ultimo ? "\\-- " : "|-- ") + etiqueta + hijo.getDato());
        mostrarHijos(hijo, prefijo + (ultimo ? "    " : "|   "));
    }
}
