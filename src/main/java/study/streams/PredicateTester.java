/* A Predicate is a functional Interface that defines a condition that a given object must meet. */
package study.streams;

import study.pojo.Book;

import java.util.List;
import java.util.function.Predicate;

public class PredicateTester {

    private static final List<Book> lsBooks = List.of(
            new Book("Sci-Fi", "War of the worlds", 600),
            new Book("Horror", "IT", 523),
            new Book("Romance", "One day for love", 600),
            new Book("Fantasy", "Wizard of Oz", 1000),
            new Book("Sci-Fi", "Jumper", 250)
    );


    public static void main(String[] args) {
//        filterByCategoria("Sci-Fi");
        filterByComplex();
    }


    /** Simple Predicate that receives a categoria and apply rule */
    private static Predicate<Book> predCategoria(String categoria){
        return ( Book libro) -> {
          return libro.categoria().equals(categoria); //Rule
        };
    }

    /**
     * Displays only books that matches the Category
     * @param category
     */
    protected static void filterByCategoria(String category){
        lsBooks.stream()
                .filter( predCategoria(category) )
                .map(Book::titulo) //.map( libro -> libro.titulo() )
                .forEach(System.out::println);
    }

    /* ****************************************** */

    /**
     * private Method that combines 3 predicates, into a single predicate using a combination of them
     * @param libro
     * @return
     */
    private static boolean isThatAGoodBook(Book libro){
        Predicate<Book> p1 = (Book l) -> l.categoria().equals("Sci-Fi");
        Predicate<Book> p2 = (Book l) -> l.categoria().equals("Fantasy");
        Predicate<Book> p3 = (Book l) -> l.paginas() > 550;

        Predicate<Book> pGoodBook = p1.or(p2).and(p3); //if has one selected category AND minimum pages
        return pGoodBook.test(libro);
    }

    /**
     * Test the filtering using the Predicates combination
     */
    protected static void filterByComplex(){
        lsBooks.stream()
                .filter(
                        libro -> isThatAGoodBook(libro)
                )
                .map( libro -> libro.titulo()+" ("+libro.paginas()+")")
                .forEach(System.out::println);
    }


}
