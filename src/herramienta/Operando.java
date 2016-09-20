
package herramienta;

public class Operando {
    private String nombre;
    private int contador;
    
    public Operando(String nombre){
    this.nombre=nombre;
    contador=0;
    }
    
    public void contar(int cant){
        contador+=cant;
    }

    public String getNombre() {
        return nombre;
    }

    public int getContador() {
        return contador;
    }   
    
    
}
