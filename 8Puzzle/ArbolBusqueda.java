import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ArbolBusqueda {
    private Nodo raiz;
    private String estadoInicial;
    private String estadoObjetivo;

    public ArbolBusqueda(String estadoInicial, String estadoObjetivo) {
        this.estadoInicial = estadoInicial;
        this.estadoObjetivo = estadoObjetivo;
        this.raiz = new Nodo(estadoInicial, null);
    }

    public void busquedaEnAnchura() {
        long tiempoInicio = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long memoriaInicio = runtime.totalMemory() - runtime.freeMemory();

        //Crea un conjunto para almacenar los nodos visitados y evitar ciclos
        Set<String> visitados = new HashSet<>();
        //Busca el nodo raiz y agregarlo a la cola
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        visitados.add(raiz.getEstado());
        Nodo nodoActual;
        //Mientras que la cola no este vacia, hacer lo siguiente:
        while (!cola.isEmpty()) {
            nodoActual = cola.poll();

            //Sacar el primer nodo de la cola y verificar si es el nodo objetivo
            if (nodoActual.getEstado().equals(estadoObjetivo)) {
                System.out.println("¡Estado objetivo encontrado!\n");
                imprimirCamino(nodoActual);
                imprimirMetricas(nodoActual, tiempoInicio, memoriaInicio);
                return;
            }

            // Si no es el nodo objetivo, expandir el nodo y agregar sus hijos a la cola
            List<Nodo> hijos = HerramientasNodo.generarHijos(nodoActual);
            for (Nodo hijo : hijos) {
                // MEJORA: Marcar como visitado e insertar a la cola AL MISMO TIEMPO.
                // Esto evita meter duplicados repetidos a la cola antes de que sean procesados.
                if (!visitados.contains(hijo.getEstado())) {
                    visitados.add(hijo.getEstado());
                    cola.add(hijo);
                }
            }
        }

        // Si la cola se vacia sin encontrar solucion
        System.out.println("No se encontro solucion.");
        imprimirMetricas(null, tiempoInicio, memoriaInicio);
    }

    private void imprimirMetricas(Nodo nodoMeta, long tiempoInicio, long memoriaInicio) {
        Runtime runtime = Runtime.getRuntime();
        long memoriaFinal = runtime.totalMemory() - runtime.freeMemory();
        long memoriaUsada = Math.max(0, memoriaFinal - memoriaInicio);
        long tiempoTranscurrido = (System.nanoTime() - tiempoInicio) / 1_000_000;

        int pasos = (nodoMeta != null) ? nodoMeta.getProfundidad() : 0;

        System.out.println("--------------------------------");
        System.out.println("Pasos necesarios: " + pasos);
        System.out.println("Tiempo de ejecucion: " + tiempoTranscurrido + " ms");
        System.out.println("Memoria estimada utilizada: " + memoriaUsada + " bytes");
        System.out.println("--------------------------------");
    }

    // Imprime de forma recursiva los tableros desde la raiz hasta el objetivo
    private void imprimirCamino(Nodo nodo) {
        if (nodo == null) {
            return;
        }
        imprimirCamino(nodo.getPadre());
        System.out.println(HerramientasNodo.formatearEstado(nodo.getEstado()));
    }
}
