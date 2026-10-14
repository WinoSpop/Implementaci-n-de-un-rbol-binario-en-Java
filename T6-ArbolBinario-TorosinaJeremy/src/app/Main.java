package app;

import java.text.Normalizer;
import java.util.Scanner;

import modelo.Nodo;
import negocio.ArbolBinario;

/**
 * Programa principal: menu interactivo para trabajar con el arbol
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
                    System.out.println("\u00C1rbol inicial creado.");
                    break;
                case 2:
                    if (hayArbol(arbol)) {
                        arbol.mostrarArbol();
                    }
                    break;
                case 3:
                    if (hayArbol(arbol)) {
                        System.out.println("N\u00FAmero de nodos: " + arbol.contarNodos());
                    }
                    break;
                case 4:
                    if (hayArbol(arbol)) {
                        System.out.println("N\u00FAmero de hojas: " + arbol.contarHojas());
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
                    System.out.println("Opci\u00F3n no v\u00E1lida. Intente de nuevo.");
            }
        } while (opcion != 0);

        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== \u00C1RBOL DE DEPENDENCIAS UNIVERSITARIAS =====");
        System.out.println("1. Crear el \u00E1rbol inicial");
        System.out.println("2. Visualizar el \u00E1rbol");
        System.out.println("3. Consultar el n\u00FAmero de nodos");
        System.out.println("4. Consultar el n\u00FAmero de hojas");
        System.out.println("5. Consultar la altura");
        System.out.println("6. Consultar el grado de un nodo");
        System.out.println("7. Verificar si un nodo es hoja");
        System.out.println("8. Ejecutar los casos de prueba");
        System.out.println("0. Salir");
        System.out.print("Elija una opci\u00F3n: ");
    }

    /** Construye el arbol de 9 nodos que pide la practica. */
    private static ArbolBinario<String> construirArbolInicial() {
        ArbolBinario<String> arbol = new ArbolBinario<>();

        Nodo<String> universidad = arbol.crearRaiz("UNIVERSIDAD");

        Nodo<String> fisei = arbol.agregarIzquierdo(universidad, "FISEI");
        Nodo<String> dide = arbol.agregarDerecho(universidad, "DIDE");

        Nodo<String> software = arbol.agregarIzquierdo(fisei, "SOFTWARE");
        arbol.agregarDerecho(fisei, "TI");

        arbol.agregarIzquierdo(dide, "INVEST.");
        arbol.agregarDerecho(dide, "VINCULACI\u00D3N");

        arbol.agregarIzquierdo(software, "NIVEL1");
        arbol.agregarDerecho(software, "NIVEL3");

        return arbol;
    }

    private static boolean hayArbol(ArbolBinario<String> arbol) {
        if (arbol.esVacio()) {
            System.out.println("El \u00E1rbol est\u00E1 vac\u00EDo. Primero use la opci\u00F3n 1.");
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
                System.out.println(nodo.getDato() + " S\u00CD es una hoja.");
            } else {
                System.out.println(nodo.getDato() + " NO es una hoja.");
            }
        }
    }

    /** Pide el nombre de un nodo y lo busca (sin importar mayusculas ni tildes). */
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

        // 1. Arbol vacio
        System.out.println();
        System.out.println("Caso 1: \u00E1rbol vac\u00EDo");
        ArbolBinario<String> vacio = new ArbolBinario<>();
        verificar("\u00BFEst\u00E1 vac\u00EDo?", true, vacio.esVacio());
        verificar("Nodos", 0, vacio.contarNodos());
        verificar("Altura", -1, vacio.altura());

        // 2. Arbol con un solo nodo
        System.out.println();
        System.out.println("Caso 2: \u00E1rbol con un solo nodo");
        ArbolBinario<String> unico = new ArbolBinario<>();
        unico.crearRaiz("UNIVERSIDAD");
        verificar("Nodos", 1, unico.contarNodos());
        verificar("Hojas", 1, unico.contarHojas());
        verificar("Altura", 0, unico.altura());

        // 3. Arbol inicial
        System.out.println();
        System.out.println("Caso 3: \u00E1rbol inicial");
        ArbolBinario<String> inicial = construirArbolInicial();
        inicial.mostrarArbol();
        verificar("Nodos", 9, inicial.contarNodos());
        verificar("Hojas", 5, inicial.contarHojas());
        verificar("Altura", 3, inicial.altura());

        // 4. Insertar en una posicion ocupada
        System.out.println();
        System.out.println("Caso 4: insertar un hijo en una posici\u00F3n ocupada");
        Nodo<String> fisei = inicial.buscar("FISEI");
        try {
            inicial.agregarIzquierdo(fisei, "OTRO");
            System.out.println("[FALLO] Se permiti\u00F3 insertar en una posici\u00F3n ocupada.");
        } catch (IllegalStateException e) {
            System.out.println("[OK] Se rechaz\u00F3 la inserci\u00F3n: " + e.getMessage());
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
