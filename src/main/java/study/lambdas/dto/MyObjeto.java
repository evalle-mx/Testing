package study.lambdas.dto;

public class MyObjeto {
    private String nombre;
    private double estatura;

    public void setNombre(String name){
        this.nombre=name;
    }

    public void saludar(){
        System.out.println("Hola, mi nombre es "+ this.nombre);
    }

    public MyObjeto(){}
    public MyObjeto(String nombre, double estatura){
        this.nombre=nombre;
        this.estatura=estatura;
    }

    public void imprimir(String texto){
        System.out.println("Texto: "+ texto);
    }

    @Override
    public String toString(){
        return "MyObject{nombre="+this.nombre+", estatura="+this.estatura+"} ";
    }
}
