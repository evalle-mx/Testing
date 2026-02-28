package org.example.testers;

import java.util.*;
import java.util.stream.Collectors;

public class StaticTests {

    public static void subMessage() {
        String message = "Hello world!";
        String newMessage = message.substring(6,12) + message.substring(12,6); // Runtime exception index 12 is out of range
        System.out.println(newMessage);
    }

    public static void SortArrayList(){
        List<String> lsNames = Arrays.asList("Pedro","Ana","Zamila","Hugo","Bruno","Alice", "Bob", "Charlie");
//        lsNames.forEach(name -> System.out.println(name));

//        Collections.sort(lsNames);  //OK
//        lsNames.sort( Comparator.comparing(String :: toString));   //OK

//        lsNames.sort(List.DESCENDING); // ==> Cannot resolve symbol 'DESCENDING'
//        lsNames.forEach(System.out::println);
        List<String> lsNames2 = lsNames.stream().sorted( (s1, s2) -> s1.compareTo(s2)) .collect(Collectors.toList() ); lsNames2.forEach(System.out::println);//OK
    }

    public static void myCharacterTest(String myString) {
        char myCharacter = myString.charAt(3);
        System.out.println(myCharacter);
    }

    public static void queueTest() {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        queue.add(4);
        queue.add(2);
        queue.add(3);
        queue.add(1);
        while(queue.isEmpty() == false ) {
            System.out.printf( "%d ", queue.remove() ); //1 2 3 4
        }
    }

    public static void SizeMap(){
        Map<String, Integer> mapa = new HashMap<>();
        mapa.put("guitar", 1200); mapa.put("cello", 3000); mapa.put("drum", 2000);

        mapa.put("cello", 4500);
        System.out.println(mapa.size());
        System.out.println(mapa);
    }

    public static void pantryMap() {
        HashMap<String, Integer> pantry = new HashMap<>();
        pantry.put("Apples", 3);
        pantry.put("Oranges", 2);
        int currenApples = pantry.get("Apples");
        pantry.put("Apples", currenApples+4 );
        System.out.println( pantry.get("Apples") );
    }

    public static void orderStrings() {
        String[] array = {"abc", "2", "10", "0"};
        List<String> list = Arrays.asList(array);
        Collections.sort(list);
        System.out.println( Arrays.toString( array ) );
    }

    static int count = 0;
    public static void countRecursive(String[] args) {
        if(count < 3) {
            count++;
            countRecursive(null);
        }
        else {   return;  }
        System.out.println("Hello World!"); // Usando System.out.println("Hello World!"+count); se entiende porque
    }

    public static void boolList() {
        List<Boolean> list = new ArrayList<Boolean>();
        list.add(true);
        list.add( Boolean.parseBoolean("FalSe") );
        list.add( Boolean.TRUE );
        System.out.print( list.size() );
        System.out.print( list.get(1) instanceof Boolean );
    }


    public static void bNifty(String nifty){
//        System.out.println(nifty.getType().equals("String"));
//        System.out.println(nifty.getType() == "String");
        System.out.println(nifty instanceof String);
        System.out.println(nifty.getClass().getSimpleName() == "String");

    }

    public static void tryCatchOrder() {
        try {
            System.out.println("A");
            badMethod();
            System.out.println("B");
        }
        catch (Exception e) {
            System.out.println("C");
        }
        finally {
            System.out.println("D");
        }
        // *exception* A D ::  Error != Exception
    }
    private static void badMethod() {
        throw new Error();
    }


//    public static void exceptionDemo() {
//        try {
//            System.out.println("Hello World");
//        }catch (Exception e) {
//            System.out.println("e");
//        }catch (ArithmeticException e) {
//            System.out.println();
//        }finally {
//            System.out.println("!");
//        }
//    }


    public static void main(String[] args) {
//        subMessage();
//        SortArrayList();
//        myCharacterTest("piper");
//        System.out.println("hello my friends".split(" ")[0]);
//        queueTest();
//        SizeMap();
//        pantryMap();
//        orderStrings();
//        countRecursive(null);
//        boolList();
//        bNifty("nifty");
//        tryCatchOrder();



    }




}
