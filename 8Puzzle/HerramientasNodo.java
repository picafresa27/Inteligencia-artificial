import java.util.ArrayList;
import java.util.List;

public class HerramientasNodo {
    private static String intercambiarPosiciones(String estado, int pos1, int pos2) {
        char[] arreglo = estado.toCharArray();
        char temporal = arreglo[pos1];
        arreglo[pos1] = arreglo[pos2];
        arreglo[pos2] = temporal;
        return new String(arreglo);
    }

    // Genera todos los movimientos posibles (hijos) a partir del estado actual
    public static List<Nodo> generarHijos(Nodo nodoPadre) {
        List<Nodo> sucesores = new ArrayList<>();
        
        // Buscar en que posicion (0 a 8) esta el espacio en blanco
        int posicionEspacio = nodoPadre.getEstado().indexOf(" ");

        // Matriz de adyacencias: define a que indices se puede mover el espacio en blanco
        int[][] posicionesAdyacentes = {
            {1, 3},       // Posicion 0: puede mover a la derecha(1) y abajo(3)
            {0, 2, 4},    // Posicion 1: izquierda(0), derecha(2), abajo(4)
            {1, 5},       // Posicion 2: izquierda(1), abajo(5)
            {0, 4, 6},    // Posicion 3: arriba(0), derecha(4), abajo(6)
            {1, 3, 5, 7}, // Posicion 4: arriba(1), izquierda(3), derecha(5), abajo(7)
            {2, 4, 8},    // Posicion 5: arriba(2), izquierda(4), abajo(8)
            {3, 7},       // Posicion 6: arriba(3), derecha(7)
            {4, 6, 8},    // Posicion 7: arriba(4), izquierda(6), derecha(8)
            {5, 7}        // Posicion 8: arriba(5), izquierda(7)
        };

        // Crear los nuevos estados para cada casilla adyacente valida
        for (int posAdyacente : posicionesAdyacentes[posicionEspacio]) {
            String nuevoEstado = intercambiarPosiciones(nodoPadre.getEstado(), posicionEspacio, posAdyacente);
            sucesores.add(new Nodo(nuevoEstado, nodoPadre));
        }

        return sucesores;
    }

    // Formatea la cadena de 9 caracteres en una cuadricula 3x3 visual
    public static String formatearEstado(String estado) {
        StringBuilder formateado = new StringBuilder();
        for (int i = 0; i < estado.length(); i++) {
            formateado.append(estado.charAt(i));
            if ((i + 1) % 3 == 0) {
                formateado.append("\n");
            } else {
                formateado.append(" ");
            }
        }
        return formateado.toString();
    }
}
