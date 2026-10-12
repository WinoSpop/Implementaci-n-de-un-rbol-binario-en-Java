package app;

import java.text.Normalizer;
import java.util.Scanner;

import modelo.Nodo;
import negocio.ArbolBinario;

/**
 * Programa principal: menú interactivo para trabajar con el árbol
 * de dependencias de la universidad.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArbolBinario<String> arbol = new ArbolBinario<>();
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(sc);
            System.out.println();

            switch (opcion) {
                case 1:
                    arbol = construirArbolInicial();
                    System.out.println("Árbol inicial creado.");
                    break;
                case 2:
                    if (hayArbol(arbol)) {
                        arbol.mostrarArbol();
                    }
                    break;
                case 3:
                    if (hayArbol(arbol)) {
                        System.out.println("Número de nodos: " + arbol.contarNodos());
                    }
                    break;
                case 4:
                    if (hayArbol(arbol)) {
                        System.out.println("Número de hojas: " + arbol.contarHojas());
                    }
                    break;
                case 5:
                    if (hayArbol(arbol)) {
                        System.out.println("Altura (en aristas): " + arbol.altura());
                    }
                    break;
                case 6:
                    if (hayArbol(arbol)) {
                        consultarGrado(sc, arbol);
                    }
                    break;
                case 7:
                    if (hayArbol(arbol)) {
                        consultarHoja(sc, arbol);
                    }
                    break;
                case 8:
                    ejecutarCasosDePrueba();
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== ÁRBOL DE DEPENDENCIAS UNIVERSITARIAS =====");
        System.out.println("1. Crear el árbol inicial");
        System.out.println("2. Visualizar el árbol");
        System.out.println("3. Consultar el número de nodos");
        System.out.println("4. Consultar el número de hojas");
        System.out.println("5. Consultar la altura");
        System.out.println("6. Consultar el grado de un nodo");
        System.out.println("7. Verificar si un nodo es hoja");
        System.out.println("8. Ejecutar los casos de prueba");
        System.out.println("0. Salir");
        System.out.print("Elija una opción: ");
    }

    /** Construye el árbol de 9 nodos que pide la práctica. */
    private static ArbolBinario<String> construirArbolInicial() {
        ArbolBinario<String> arbol = new ArbolBinario<>();

        Nodo<String> universidad = arbol.crearRaiz("UNIVERSIDAD");

        Nodo<String> fisei = arbol.agregarIzquierdo(universidad, "FISEI");
        Nodo<String> dide = arbol.agregarDerecho(universidad, "DIDE");

        Nodo<String> software = arbol.agregarIzquierdo(fisei, "SOFTWARE");
        arbol.agregarDerecho(fisei, "TI");

        arbol.agregarIzquierdo(dide, "INVEST.");
        arbol.agregarDerecho(dide, "VINCULACIÓN");

        arbol.agregarIzquierdo(software, "NIVEL1");
        arbol.agregarDerecho(software, "NIVEL3");

        return arbol;
    }

    private static boolean hayArbol(ArbolBinario<String> arbol) {
        if (arbol.esVacio()) {
            System.out.println("El árbol está vacío. Primero use la opción 1.");
            return false;
        }
        return true;
    }

    private static void consultarGrado(Scanner sc, ArbolBinario<String> arbol) {
        Nodo<String> nodo = pedirNodo(sc, arbol);
        if (nodo != null) {
            System.out.println("El grado de " + nodo.getDato() + " es: " + nodo.grado());
        }
    }

    private static void consultarHoja(Scanner sc, ArbolBinario<String> arbol) {
        Nodo<String> nodo = pedirNodo(sc, arbol);
        if (nodo != null) {
            if (nodo.esHoja()) {
                System.out.println(nodo.getDato() + " SÍ es una hoja.");
            } else {
                System.out.println(nodo.getDato() + " NO es una hoja.");
            }
        }
    }

    /** Pide el nombre de un nodo y lo busca (sin importar mayúsculas ni tildes). */
    private static Nodo<String> pedirNodo(Scanner sc, ArbolBinario<String> arbol) {
        System.out.print("Escriba el nombre del nodo: ");
        String buscado = normalizar(sc.nextLine());
        Nodo<String> nodo = arbol.buscar(dato -> normalizar(dato).equals(buscado));
        if (nodo == null) {
            System.out.println("No existe un nodo con ese nombre.");
        }
        return nodo;
    }

    private static String normalizar(String texto) {
        String sinTildes = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return sinTildes.trim().toUpperCase();
    }

    private static int leerEntero(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ------------------------------------------------------------------
    // Casos de prueba obligatorios
    // ------------------------------------------------------------------

    private static void ejecutarCasosDePrueba() {
        System.out.println("=== CASOS DE PRUEBA ===");

        // 1. Árbol vacío
        System.out.println();
        System.out.println("Caso 1: árbol vacío");
        ArbolBinario<String> vacio = new ArbolBinario<>();
        verificar("¿Está vacío?", true, vacio.esVacio());
        verificar("Nodos", 0, vacio.contarNodos());
        verificar("Altura", -1, vacio.altura());

        // 2. Árbol con un solo nodo
        System.out.println();
        System.out.println("Caso 2: árbol con un solo nodo");
        ArbolBinario<String> unico = new ArbolBinario<>();
        unico.crearRaiz("UNIVERSIDAD");
        verificar("Nodos", 1, unico.contarNodos());
        verificar("Hojas", 1, unico.contarHojas());
        verificar("Altura", 0, unico.altura());

        // 3. Árbol inicial
        System.out.println();
        System.out.println("Caso 3: árbol inicial");
        ArbolBinario<String> inicial = construirArbolInicial();
        inicial.mostrarArbol();
        verificar("Nodos", 9, inicial.contarNodos());
        verificar("Hojas", 5, inicial.contarHojas());
        verificar("Altura", 3, inicial.altura());

        // 4. Insertar en una posición ocupada
        System.out.println();
        System.out.println("Caso 4: insertar un hijo en una posición ocupada");
        Nodo<String> fisei = inicial.buscar("FISEI");
        try {
            inicial.agregarIzquierdo(fisei, "OTRO");
            System.out.println("[FALLO] Se permitió insertar en una posición ocupada.");
        } catch (IllegalStateException e) {
            System.out.println("[OK] Se rechazó la inserción: " + e.getMessage());
        }
        verificar("Nodos siguen igual", 9, inicial.contarNodos());

        // 5. Grado de FISEI y SOFTWARE
        System.out.println();
        System.out.println("Caso 5: grado de FISEI y SOFTWARE");
        verificar("Grado de FISEI", 2, inicial.buscar("FISEI").grado());
        verificar("Grado de SOFTWARE", 2, inicial.buscar("SOFTWARE").grado());
    }

    private static void verificar(String descripcion, Object esperado, Object obtenido) {
        String estado = esperado.equals(obtenido) ? "[OK]   " : "[FALLO]";
        System.out.println(estado + " " + descripcion
                + " -> esperado: " + esperado + ", obtenido: " + obtenido);
    }
}
