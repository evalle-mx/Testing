package study.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamTester {

    public static void main(String[] args) {

        collectorToListDemo();

        collectorToSetDemo();

        collectorsJoining();

        collectorCounting();

        collectorPartitioningDemo();

        streamParallelDemo();
    }

    /**
     * Usando Parallel en Stream se envia a diferentes nucleos del procesador, para ejecutar en paralelo
     * Ventaja: Velocidad, Desventaja: No respeta un orden/secuencia
     */
    private static void streamParallelDemo() {
        
        int[] numeros  = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

        // Stream secuencial
        long start = System.nanoTime();
        Arrays.stream(numeros).forEach( n -> System.out.println(" Seq "+n ));
        long end = System.nanoTime();
        long seqTime = end - start;
        System.out.println("\n Tiempo secuencial: " + (seqTime) + " nanosegundos \n\n");


        // Parallel Stream
        start = System.nanoTime();
        Arrays.stream(numeros).parallel().forEach( n -> System.out.println(" Par "+n ));
        end = System.nanoTime();
        System.out.println("\n Tiempo secuencial: " + (end-start) + " nanosegundos ");

        System.out.println("Diferencia de tiempo " + (seqTime - (end-start)));

    }


    /**
     * Splits in 2 categories
     */
    private static void collectorPartitioningDemo() {
        /* Collectors PARTITIONING BY (True & False) */
        List<Integer> edades = List.of(13,25,14,51,10,35,9,25,34,8,99);

        // Splits by condition in two groups:
        int mayoriaEdad = 18;
        Map<Boolean, List<Integer>> mapMayores = edades.stream()
                .collect( Collectors.partitioningBy( e -> e>=mayoriaEdad));

        System.out.println(mapMayores);
    }

    private static void collectorCounting() {
        /* Collectors counting */
        List<String> lsPaises = Arrays.asList("Canada, Armenia, Bolivia, Mexico, USA, Netherlands, Germany, Japan, Russia, North Corea, Laos, Luxemburgo, Argentina".split(", "));
        //Count countries that start With 'L'
        String token = "L";
        Long count = lsPaises.stream()
                .filter(pais -> pais.toUpperCase().startsWith(token))
//                        .collect(Collectors.counting());
                .count();

        System.out.println("Hay "+count+" paises que empiezan con " + token); //*/
    }

    private static void collectorsJoining() {
        /* Collectors JOINING */
        List<String> gestoresBds = List.of("MySQL", "MongoDB", "PostgreSQL", "Access", "Oracle");
        String output = gestoresBds.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(" | "));
        System.out.println(output); // */
    }

    private static void collectorToSetDemo() {
        /* / Collectors toSET (To avoid duplicates) // */
        List<String> langs = List.of("Java", "Python", "C#",  "angular", "node", "pHp", "C#", "C#","java", "javascript");

        //Eliminar repetidos y convertir a mayusculas
        Set<String> langsSet = langs.stream()
                .map(String::toUpperCase)
                .collect( Collectors.toSet()) ;

        langsSet.forEach(System.out::println); //*/
    }

    private static void collectorToListDemo() {
        /* Collector.toList // */
        List<String> nombres = List.of("Juan", "Pepe", "Maria", "Pedro", "Carlos", "Guillermo", "Laura", "Violeta", "Ana", "Benjamin", "Jennifer");
        //nombres.forEach(System.out::println);
        // mostrar los nombres con A
        nombres.stream().filter( n -> n.contains("a")).toList().forEach(System.out::println); //*/
    }
}