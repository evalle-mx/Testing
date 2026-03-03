package study.exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Supermercado {

    //Manzana,frutas,1,2.5
    public record Producto(String name, String category, Integer quantity, Double price) { }

    public static void main(String[] args) {
        // 1) obtain list of Products (Reading from csv)
        List<Producto> lsProds = getProductsList();

        // 2) Filter products by Category ("Frutas")
        List<Producto> lsProdsFilteredCat = filterByCategory("Lacteos", lsProds);
        lsProdsFilteredCat.forEach(System.out::println);

       // 3) Calculate TOTAL price for all products in "Lacteos" (PENDING)
        showTotalPriceCategory("Lacteos", lsProds);

       // 4) Get the product with LOWEST PRICE
        Producto cheaper = getCheaperProduct(lsProds);

        // 5) Group products per category
        Map<String, List<Producto>> groupedProds = getGroupedProducts(lsProds);

        // 6) Calculate the average price of products of each category, then find the one with the highest price
          //-- Al ser una solicitud que puede manejar muchos datos y puede demorar se sugiere utilizar Parallel Streams
//        showAvgPriceCategoryAndHighestPrice(groupedProds);
        showAvgPriceCategoryAndHighestPriceBYParallel(lsProds);
    }





    /**
     * Filters the products for a Category
     * @param category
     * @param lsProducts
     * @return
     */
    private static List<Producto> filterByCategory(String category, List<Producto> lsProducts) {
//        lsProducts.forEach(System.out::println);
        System.out.println("\n >> Filtering products by category: " +category);

        return lsProducts.stream()
                .filter( prod -> prod.category().equalsIgnoreCase(category)).toList();
                //.forEach(System.out::println);
    }

    /**
     * Display the total price of products for a Category
     * @param category
     */
    private static void showTotalPriceCategory(String category, List<Producto> lsProducts) {
        System.out.println("\n >> Show total Price for products by category: " +category);
        double countCat = lsProducts.stream()
                .filter( prod -> prod.category().equalsIgnoreCase(category) )
                .mapToDouble( Producto::price )
                .sum();
        System.out.println("Total Price for products by category " +category + " : " + countCat );
    }

    /**
     * It will obtain the cheapest (Lower price) product from the list
     * @param lsProds
     * @return
     */
    private static Producto getCheaperProduct(List<Producto> lsProds) {
        System.out.println("\n >> Cheaper Product is");
        Producto cheaperProduct = lsProds.stream()
                .min(Comparator.comparingDouble(Producto::price))
                .orElse(null); //In case list is empty
        System.out.println(cheaperProduct);
        return cheaperProduct;
    }

    /**
     * Return a Map with Products classified by Category
     * @param lsProds
     * @return
     */
    private static Map<String, List<Producto>> getGroupedProducts(List<Producto> lsProds) {
        System.out.println("\n >> Products grouped by Category");
        Map<String, List<Producto>> mapProds = lsProds.stream()
                .collect( Collectors.groupingBy(Producto::category) );
        System.out.println(mapProds.toString().replace("]],", "]]\n"));
        return mapProds;
    }

    /**
     * Calculate the average price of products of each category, then find the one with the highest price
     * @param groupedProds
     */
    private static void showAvgPriceCategoryAndHighestPrice(Map<String, List<Producto>> groupedProds) {
        System.out.println("\n >> Show Average Price by Category & Highest Price Category");

    }

    private static void showAvgPriceCategoryAndHighestPriceBYParallel(List<Producto> lsProds) {
        System.out.println("\n >> Show Average Price by Category & Highest Price Category");

        Map<String, Double> mpCatPrecio = lsProds
                    .parallelStream()
                    .collect( Collectors.groupingBy(
                            Producto::category,   // GroupBy-ID, -> Key (String)
                            Collectors.averagingDouble(Producto::price) // Calculation-Group -> Value (Double)
                    ));
        System.out.println("Average per Category: "+mpCatPrecio);
        String maxPriceCat = mpCatPrecio
                .entrySet().stream()
                .max( Comparator.comparingDouble(Map.Entry::getValue) )
                //.get().getKey();
                .map( Map.Entry::getKey).orElse("Not available");

        System.out.println("Category with highest price: "+ maxPriceCat );
    }
/* ************************************** */
    /**
     * Obtains a list of Producto-object from a CSV file
     * @return
     */
    private static List<Producto> getProductsList() {
        List<Producto> lsProductos = new ArrayList<>();
        Path path = Path.of( "/Users/ernesto.valle/Workspace/Java/Testing/src/main/resources/testfiles/Productos.csv"  );
        try {
            List<String>  lslines = Files.readAllLines(path);
            lslines.forEach( lProd -> {
                String[] sToken = lProd.split(",");
                lsProductos.add(  new Producto(sToken[0], sToken[1], Integer.parseInt(sToken[2]), Double.parseDouble(sToken[3]) ) );
            });  //System.out::println
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
         try (InputStream inpSt = GestorVehiculos.class.getResourceAsStream(REL_PATHFILE)) {

            if (inpSt == null) {
                throw new IOException("Cannot find resource file at: " + REL_PATHFILE);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inpSt, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");

                    lsVehicles.add(new Vehicle(tokens[0], tokens[1], Integer.parseInt(tokens[2])));
                }
            }
        }
         */
        return lsProductos;
    }

}
