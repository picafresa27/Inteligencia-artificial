public class Arbol{
    Nodo raiz;
    public Arbol{
        raiz = null;
    }


    public void insertar(int i, int numero){
        Nodo n = new Nodo(i);
        n.contenido = numero;

        if(raiz==null){
            raiz=n;
        }else{
            Nodo aux = raiz;
        }
    }
}