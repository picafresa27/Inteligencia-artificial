public class Dados{
    int numCaras;
    int tiradas[];

    public Dados(int numCaras, int numDados){
        this.numCaras = numCaras;
        tiradas = new int [numDados];
    }

    public void tirarDados(){
        for(int i=0; i<tiradas.length; i++){
            tiradas[i] = (int)(Math.random()*numCaras+1);
        }
        comprobarIguales();
    }

    private void comprobarIguales(){
        boolean iguales = true;
        int numAComprobar = tiradas[0];
        for(int i=0; i<tiradas.length; i++){
            System.out.print("[" + tiradas[i] + "]");
            if(numAComprobar != tiradas[i]) iguales = false;
        }
        if(iguales) System.out.print("Todos los numeros son iguales. ¡Que suerte!");
        else System.out.print("No son iguales, mas suerte a la proxima :( ");
    }
}