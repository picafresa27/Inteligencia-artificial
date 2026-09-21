public class Nodo {
    private String estado;
    private Nodo padre;
    private int profundidad;

    public Nodo(String estado, Nodo padre) {
        this.estado = estado;
        this.padre = padre;
        // Si tiene padre, la profundidad se incrementa en 1 respecto al nivel anterior
        this.profundidad = (padre == null) ? 0 : padre.getProfundidad() + 1;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Nodo getPadre() {
        return padre;
    }

    public void setPadre(Nodo padre) {
        this.padre = padre;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }
}