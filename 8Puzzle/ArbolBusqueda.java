import java.util.*;

public class ArbolBusqueda {
    private Nodo raiz;
    private String estadoObjetivo;

    public ArbolBusqueda(String estadoInicial, String estadoObjetivo) {
        this.estadoObjetivo = estadoObjetivo;
        this.raiz = new Nodo(estadoInicial, null);
    }

    public void busquedaEnAnchura() {
        ejecutarBusqueda(new LinkedList<>(), "Anchura");
    }

    public void busquedaEnProfundidad() {
        ejecutarBusqueda(Collections.asLifoQueue(new ArrayDeque<>()), "Profundidad");
    }

    public void busquedaCostoUniforme() {
        ejecutarBusqueda(new PriorityQueue<>(Comparator.comparingInt(Nodo::getProfundidad)), "Costo Uniforme");
    }

    // Inicio de busqueda
    private void ejecutarBusqueda(Queue<Nodo> estructura, String nombreAlgoritmo) {
        long tiempoInicio = System.nanoTime();
        //almacena los estados ya visitados para evitar repetirlos
        Set<String> visitados = new HashSet<>();

        estructura.add(raiz);
        visitados.add(raiz.getEstado());

        while (!estructura.isEmpty()) {
            //visita el sig nodo
            Nodo nodoActual = estructura.poll(); 

            //ya estamos en el orden correcto?
            if (nodoActual.getEstado().equals(estadoObjetivo)) {
                System.out.println("¡Solución encontrada con " + nombreAlgoritmo + "!\n");
                imprimirCamino(nodoActual);
                imprimirMetricas(nodoActual, tiempoInicio, visitados.size());
                return;
            }

            // Expandir hijos
            for (Nodo hijo : HerramientasNodo.generarHijos(nodoActual)) {
                if (!visitados.contains(hijo.getEstado())) {
                    visitados.add(hijo.getEstado());
                    estructura.add(hijo);
                }
            }
        }

        System.out.println("No se encontró solución con " + nombreAlgoritmo + ".");
        imprimirMetricas(null, tiempoInicio, visitados.size());
    }

    public void busquedaIterativa(int limiteMaximo) {
        long tiempoInicio = System.nanoTime();
        Set<String> visitadosGlobales = new HashSet<>();

        //desde el 0 hasta el limite
        for (int limiteActual = 0; limiteActual <= limiteMaximo; limiteActual++) {
            
            Nodo resultado = busquedaLimitada(raiz, limiteActual, visitadosGlobales);

            // Si encontró la meta en el nivel actual se finaliza
            if (resultado != null) {
                System.out.println("¡Solución encontrada con Profundidad Iterativa (Límite: " + limiteActual + ")!\n");
                imprimirCamino(resultado);
                imprimirMetricas(resultado, tiempoInicio, visitadosGlobales.size());
                return;
            }
        }

        System.out.println("No se encontró solución hasta el límite de " + limiteMaximo);
        imprimirMetricas(null, tiempoInicio, visitadosGlobales.size());
    }

    // Método auxiliar recursivo para la Profundidad
    private Nodo busquedaLimitada(Nodo nodoActual, int limite, Set<String> visitadosGlobales) {
        visitadosGlobales.add(nodoActual.getEstado());

        //caso base
        if (nodoActual.getEstado().equals(estadoObjetivo)) {
            return nodoActual;
        }

        //ya no podemos ir mas profundo
        if (limite <= 0) {
            return null;
        }

        for (Nodo hijo : HerramientasNodo.generarHijos(nodoActual)) {
            Nodo resultado = busquedaLimitada(hijo, limite - 1, visitadosGlobales);
            if (resultado != null) {
                return resultado; // Trae la solución encontrada hacia arriba
            }
        }

        return null;
    }

    private void imprimirMetricas(Nodo nodoMeta, long tiempoInicio, int nodosVisitados) {
        long tiempoTranscurrido = (System.nanoTime() - tiempoInicio) / 1_000_000;
        int pasos = (nodoMeta != null) ? nodoMeta.getProfundidad() : 0;

        System.out.println("--------------------------------");
        System.out.println("Pasos necesarios: " + pasos);
        System.out.println("Nodos visitados: " + nodosVisitados);
        System.out.println("Tiempo de ejecución: " + tiempoTranscurrido + " ms");
        System.out.println("--------------------------------");
    }

    private void imprimirCamino(Nodo nodo) {
        if (nodo == null) return;
        imprimirCamino(nodo.getPadre());
        System.out.println(HerramientasNodo.formatearEstado(nodo.getEstado()));
    }
}