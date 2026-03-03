package study.optionals;

import java.util.Optional;

public class OptionalTester {

    protected static void testOptionalNull(){
        Optional<String> optional = Optional.ofNullable(null);//Puede recibir un null o vacio
        System.out.println(optional);

        if(optional.isPresent()){
            System.out.println("El valor esta presente: " + optional.get());
        }
        if(optional.isEmpty()){
            System.out.println("El valor es vacio");
        }
        if(optional == null){
            System.out.println("El valor es NULL");
        }
    }


    protected static void testOptional2(){
        Optional<String> cadena = null;
//        Optional.empty(); //El valor es vacio
//        Optional.of(null); // Arroja excepcion en execution
//        Optional.of(""); // El valor esta presente
//        Optional.of("Dianita"); // El valor esta presente
        if(cadena == null){
            System.out.println("El valor es NULL");
        }
        if(cadena.isPresent()){
            System.out.println("El valor esta presente: " + cadena.get());
        }
        if(cadena.isEmpty()){
            System.out.println("El valor es vacio");
        }
    }


    public static void main(String[] args) {
//        testOptionalNull();
        testOptional2();
    }

}
