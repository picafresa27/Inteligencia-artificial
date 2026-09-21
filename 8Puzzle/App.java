public class App {
    public static void main(String[] args) throws Exception {
        // ' ' representa el espacio vacio del tablero 3x3
        String estadoInicial = "7621 3458"; 
        String estadoObjetivo = "12345678 "; 

        ArbolBusqueda arbol = new ArbolBusqueda(estadoInicial, estadoObjetivo);
        arbol.busquedaEnAnchura();
        
        System.out.println("Fin del programa");
    }
}
