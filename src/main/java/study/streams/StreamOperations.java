package study.streams;

//import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamOperations {

    private static final List<String> lsNames = List.of("Diego", "Saul", "adrian", "Roberto","Sonja", "Brenda", "Sattina", "silvano", "Carlos");

    private static final List<String> lsPaises = Arrays.asList("Canada, Armenia, Bolivia, Mexico, USA, Netherlands, Germany, Japan, Russia, North Corea, Argentina".split(", "));

    public static void main(String[] args) {
        /* Intermediate Operations */
//        squareNumbers_Map();
//        orderNames_sorted();
//        extractNamesStartingWith_filter();

        /*  Terminal Operations  */
        displayListNames(lsPaises);
//        squareNumbers_MapToSet();
//        reduceNumbers_Reduce();

    }


    /* ################################ Intermediate Operations  ################################ */
    /**
     * From a sequence of numbers, create a new collection with their square (x*x) values
     * Using stream
     */
    protected static void squareNumbers_Map(){
        List<Integer> lsNumber = List.of(1,2,6,5,9,8); // 1,4,36,25,81,64
        List<Integer> lsSquareNums = lsNumber.stream().map(
                num -> num*num
        ) .toList();
        /* .map  is used to return a stream consisting of the results of applying the given Function (PREDICATe) to the elements of this stream. */
        System.out.println("From list " + lsNumber );
        System.out.println("Square values are: " + lsSquareNums );

    }

    /**
     * From a given list of Names, extracts those who 'startsWith' a letter or sequence of chars
     */
    protected static void extractNamesStartingWith_filter(){
        String prefix = "Sa";

        List<String> lsFilteredNames = lsNames.stream().filter(
                name -> name.startsWith (prefix) // name.toUpperCase().startsWith(prefix)
                )
                .toList();
        /* toUpperCase it’s not recommended for large loops because it creates brand-new String objects for both the original and the prefix, which can put pressure on the Java Garbage Collector. */
        System.out.println("From list " + lsNames );
        System.out.println("names starting with "+prefix + " are: " + lsFilteredNames );
    }

    protected static void orderNames_sorted(){
        //List<String> lsNames = List.of("Diego", "Saul", "adrian", "Roberto", "Sonja", "Brenda", "Sattina", "silvano", "Carlos");
        List<String> lsOrdered = lsNames.stream()
                .sorted()  // or .sorted( java.util.Comparator.reverseOrder()) for DESC
                .toList();
        /* By default orders in ASC order, use .sorted( Comparator.reverseOrder() ) to DESC */
        System.out.println("From list " + lsNames );
        System.out.println("names ordered are: " + lsOrdered );
    }

    /* ################################ Terminal Operations  ################################ */

    protected static void displayListNames(List<String> lsWords){
        /*Order */
//        lsWords
//                .stream().sorted()
//                .forEach(System.out::println); // .stream().forEach(name -> System.out.println(name));

//        /* Filter by startsWith */
//        lsWords.stream().filter(word -> word.startsWith("A")).forEach(System.out::println);

        /* Filter if word CONTAINS a string */
        lsWords.stream().filter(word -> word.toLowerCase().contains("er")).forEach(System.out::println);
    }

    protected static void squareNumbers_MapToSet() {
        List<Integer> lsNumber = List.of(1,2,6,5,9,8); // 1,4,36,25,81,64
        Set<Integer> squareSet = lsNumber.stream()
                .map( num -> num*num ) // Same operation
                .collect(
                        Collectors.toSet() //Output to a different Collection Interface
                );
        System.out.println("From list " + lsNumber );
        System.out.println("Set square values are: " + squareSet );

        System.out.println("Displaying the square operation without creating a new List");
        lsNumber
                .stream()
                .map( x -> x*x)
                .forEach(System.out::println); //.forEach( y -> System.out.println(y) );
    }

    protected static void reduceNumbers_Reduce(){
        List<Integer> lsNumber = List.of(1,2,6,5,9,8); // 2,6,8=> 3
        int even = lsNumber
                .stream()
                .filter( x-> x%2 ==0)
                .reduce(
                        0, (ans,i) -> ans+i //ans variable is assigned 0 as the initial value and i is added to it .
                );
        System.out.println(even);
    }
}
