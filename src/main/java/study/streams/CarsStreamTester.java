/**
 * Java Streams API Explained (with examples) (https://www.youtube.com/watch?v=2StXP1XaU04)
 */

package study.streams;

import study.pojo.Car;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarsStreamTester {

    private static final List<Car> lsAllCars = List.of(
            new Car("sedan", "BMW", "530", 1988),
            new Car("hatchback", "Audi", "A10", 2500),
            new Car("sedan", "Nissan", "Sentra", 1600),
            new Car("hatchback", "Volkswagen", "Golf", 1450),
            new Car("sedan", "Toyota", "TypeR", 2988),
            new Car("hatchback", "Toyota", "Corolla", 2988)
    );

//    ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        showRecord();

//        testFiltering();
//        extractMakes();
//        streamPriorityOperations();
//        collectFunctPartioning();
//        collectFnGrouping();

    }


    private static void showRecord(){
        Car auto = new Car("sedan", "BMW", "530", 1988);
        System.out.println(auto); //toString
        System.out.printf("type: %s: (%s,%s) engine:%d", auto.type(), auto.make(), auto.model(), auto.engineCapacity());

    }


    /**
     * Extract the objects with come characteristic
     */
    protected static void testFiltering(){
        List<Car> filteredList = lsAllCars
                                .stream()
                                .filter( car -> car.type().equals("sedan") )
                                .toList();

        System.out.println("filteredList: (" +filteredList.size()+"): \n" + filteredList  );
    }

    protected static void extractMakes(){
        List<String> carMakesList = lsAllCars.stream().map( car -> car.make()).toList();
        System.out.println("carMakesList: " + carMakesList  );

        //Convert to Make & Model List: [ BMW, 530, Audi, A10, ....]
        carMakesList = lsAllCars.stream().flatMap( car -> Stream.of(car.make(), car.model()) ).toList();
        System.out.println("carMakeAndModelsList: " + carMakesList  );
    }

    /**
     * Demonstrate the order of execution on a stream
     * Priority of terminal/final operations (Not stream operations) -use Debug-
     */
    protected static void streamPriorityOperations(){
        Stream<Integer> integerStream = Stream.of(10,11,12,13,14); //Execution 1

        Stream<Integer> filteredIntegerStream = integerStream.filter( i-> {
            System.out.println("Filtering integer " + i ); //Execution 3
            return i%2 == 0; //Only pair numbers
        });

        System.out.println("Count = " + filteredIntegerStream.count() ); //Execution 2
        System.out.println("filteredIntegerStream: "+ filteredIntegerStream ); //Execution 4
    }


    /**
     * Partitiones the List to generate a map with two main keys Boolean, split of Type sedan & hatchback
     */
    protected static void collectFunctPartioning(){
        //Uses partitioning to generate a map of two groups by Boolean (Sedan and No-Sedan)
        Map<Boolean, List<Car> > partitionedCars = lsAllCars.stream().collect(
                Collectors.partitioningBy( car -> car.type().equals("sedan") )
        );

        System.out.println("Partitioned cars: " + partitionedCars +"\n");
        System.out.println(partitionedCars.get(Boolean.TRUE));
        System.out.println(partitionedCars.get(Boolean.FALSE));
    }

    /**
     * Groups the cars by Type
     */
    protected static void collectFnGrouping(){
        //type> Make, engineCapacity  ::  Map<@type, Map<@make, String>>
        Map<String, Map<String, Integer>> groupedCars = lsAllCars.stream().collect(
                Collectors.groupingBy(
                        Car::type,   //car -> car.type
                        Collectors.toMap( Car::model, Car::engineCapacity )  //toMap( car -> car.model, car -> car.engineCapacity
                )
        ) ;
        System.out.println("Grouped Cars: " + groupedCars +"\n");
    }
}
