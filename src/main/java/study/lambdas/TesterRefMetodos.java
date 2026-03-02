package study.lambdas;

import study.lambdas.dto.Empleado;
import study.lambdas.dto.Gerente;
import study.lambdas.dto.MyObjeto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class TesterRefMetodos {


    /* ################  REFERENCIA A METODOS ESTATICOS  ################ */
    /*
     * forma de apuntar a un metodo static usando la sintaxis de method reference introducida en Java 8.
     * Se usa principalmente con interfaces funcionales (como Function, Consumer, etc.) y es una alternativa más limpia a las expresiones lambda.
     */
    public static void refMetodoStatico(){
        // 1)
        //String sNumero = String.valueOf(5);
        Function<Integer, String > converter;   // Function<T,R>  T-Type input, R-ReturnType
        converter = String::valueOf;
        String sNumero = converter.apply(5);
        System.out.println(sNumero);

        // 2
        Function<Integer, Integer> cuber = TesterRefMetodos::cuadrado; //x-> cuadrado(x);
        System.out.println(cuber.apply(5));

        // 3)
        List<String> sLsNumeros = Arrays.asList("1,5,8,12,44".split(","));
        List<Integer> iLsNumeros = sLsNumeros.stream()
                .map( Integer::parseInt )// ==> .map( num -> Integer.parseInt(num) )
                .toList();
        iLsNumeros.forEach(System.out::println);

    }

    public static int cuadrado(int x){
        return x*x;
    }


    /* ################  REFERENCIA A METODO DE INSTANCIA DE UN OBJETO  ################ */
    /*
    La referencia a un método de instancia de un objeto en Java es una forma abreviada de llamar a un método no estático de un objeto ya creado, usando la sintaxis objeto::metodo.
     */
    protected static void refMetodoInstanciaObjeto(){

        MyObjeto mObj = new MyObjeto();
        mObj.setNombre("Diana");

        //Interface que executa sin esperar respuesta
        Runnable  saludo = mObj::saludar;
        saludo.run();

        List<String> lsNombres = List.of("Ana", "Luis", "Sofia");
        lsNombres.forEach(mObj::imprimir); // forEach( nom -> mObj.imprimir(nom) )

    }

    /* ################  REFERENCIA A CONSTRUCTOR  ################ */


    protected static void refAConstructor(){
        BiFunction<String, Double, MyObjeto> crearObjeto = MyObjeto::new;

        MyObjeto mObject = crearObjeto.apply("Violeta", 1.58);
        System.out.println("mObject: " + mObject );
    }


    /* ################  REFERENCIA A METODO DE INSTANCIA DE UN OBJETO ARBITRARIO  ################ */

    public static void refMethInstanciObjetoArbitrario(){
        List<MyObjeto> personas = new ArrayList<>();

        //Gracias al polimorfismo
        personas.add( new Empleado());
        personas.add( new Gerente());
        personas.add( new Empleado());
        personas.add( new MyObjeto("JOE",1.90));
        personas.add( new Gerente());


        personas.forEach( MyObjeto::saludar );
    }



    /* ______________________________________________________________*/

    public static void main(String[] args) {
//        refMetodoStatico();
//        refMetodoInstanciaObjeto();
//        refAConstructor();
        refMethInstanciObjetoArbitrario();


    }

}
