package study.lambdas;

public class TestLambdas1 {


    public static void main(String[] args) {
         Mensaje msg = (nombre, lugar) -> {
             System.out.println("Implementacion metodo unico de la Interface funcional");
             System.out.println("Hola " + nombre);
             System.out.println("Bienvenid@ a " + lugar );
         };

         msg.emitirMensaje("Netto", "Canada ");
    }
}
