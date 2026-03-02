/*
Una casa de ventras de autos desea crear una aplicacion para gestionar los autos que tiene en inventario. para ello cuenta con datos de Modelo, Marca y Costo.
* VehiclesList.csv

A partir de los datos lograr lo siguiente:
- Leer la lista de vehiculos en archivo csv
- Ordenar la lista por precio de menor a mayor (ASC o DESC) y desplegar en pantalla el resultado
- Ordenar al mismo tiempo tanto por marca como por precio (ambos criterios simultaneamente) e imprimir resultado en pantalla
- Extraer una lista de todos los vehiculos cuo precio no supere los 23,000
- FIltrar unicamente los vehiculos de la marca Chevrolet y Volkswagen.
- Mostrar todos los acutos cuyo modelo contenga por lo menos una letra 'A'
 */
package study.exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class GestorVehiculos {

    private static final String PATH_FILE = "/home/netto/Workspace/Java/Testing/src/main/resources/testfiles/VehiclesList.csv";

    public record Vehicle(String marca, String modelo, Integer precio){}

    public static void main(String[] args) {

        try {
            List<Vehicle> lsAutos = getLsAutos();
            lsAutos.forEach(System.out::println);
            System.out.println("\n\n");

            // 1) Ordenar de precio ascendente
            System.out.println("  ++ Vehicles ordered by Price DESC ::: ");
            displayOrderedByPrice(lsAutos);

            // 2) Ordenar por marca y luego precio [SORTED]
            System.out.println("  ++ Vehicles ordered by Brand,price :::");
            displayOrderByBrandAndPrice(lsAutos);

            // 3) Desplegar autos con precio menor a 23K
            Integer maxPrice = 23000;
            System.out.println("  ++ Vehicles below "+maxPrice + " :::");
            filteredByPrice(lsAutos, maxPrice).forEach(System.out::println);

            // 4) Autos de marca Chevrolet o Volkswagen
            List<String> lsBrands = List.of("Chevrolet", "Volkswagen");
            System.out.println("   ++ Vehicles from " +lsBrands  + " :::");
            filteredByBrands(lsAutos, lsBrands).forEach(System.out::println);


            // 5) Mostrar todos los acutos cuyo modelo contenga por lo menos una letra 'A'
            String token = "A"; // case no sensitive
            System.out.println("   ++ Vehicles with " + token + " on the model :::");
            displayWithStringContained(lsAutos,"A");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * OK. Obtiene la lista de vehiculos
     * @return
     * @throws IOException
     */
    protected static List<Vehicle> getLsAutos() throws IOException {

        Path path = Path.of(PATH_FILE);

        List<String> lsAutos = Files.readAllLines(path);
//        lsAutos.forEach(System.out::println);
        List<Vehicle> lsVehicles = new ArrayList<>();
        lsAutos.forEach(
                linea -> {
                    String[] tokens = linea.split(",");
                    lsVehicles.add(new Vehicle(tokens[0], tokens[1], Integer.parseInt(tokens[2]) ) );
                }
        );
        return lsVehicles;
    }


    protected static void displayOrderedByPrice(List<Vehicle> lsVehicles){
        List<Vehicle> lsOrderVeh = List.copyOf(lsVehicles); //COPY to not alterate original List
        lsVehicles.sort(Comparator.comparing( Vehicle::precio ).reversed());
        lsVehicles.forEach(System.out::println);
    }

    protected static void displayOrderByBrandAndPrice(List<Vehicle> lsVehicles){
        //List<Vehicle> lsOrderVeh = List.copyOf(lsVehicles);
        lsVehicles.stream()
                .sorted(
                        Comparator.comparing(Vehicle::marca).thenComparing(Vehicle::precio)
                ).forEach(System.out::println);
    }

    protected static List<Vehicle> filteredByPrice(List<Vehicle> lsVehicles, Integer maxPrice){
        List<Vehicle> lsFilterVehicles = lsVehicles.stream()
                .filter( auto -> auto.precio() <= maxPrice )
                .toList();
        return lsFilterVehicles;
    }

    protected static List<Vehicle> filteredByBrands(List<Vehicle> lsVehicles, List<String> lsBrands){
        // a) Using contains
//        return lsVehicles.stream()
//                .filter( auto -> {
//                    return lsBrands.contains(auto.marca);
//                })
//                .toList();
        //b) Using equals for perfect match
        //Using a HashSet allows for $O(1)$ constant-time lookups. When you use Set.contains(), it performs an exact,
        // "perfect match" check using the equals() and hashCode() methods of the String
        Set<String> brandSet = new HashSet<>(lsBrands);
        return lsVehicles.stream()
                .filter(auto -> brandSet.contains(auto.marca() ))
                .toList();
    }

    protected static void displayWithStringContained(List<Vehicle> lsVehicles, String token){
        lsVehicles.stream()
                .filter(
                        auto -> {
                            return token.toLowerCase().contains(auto.modelo().toLowerCase());
                        }
                ).forEach(System.out::println);
    }
}
