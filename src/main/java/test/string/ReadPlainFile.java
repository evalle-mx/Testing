package test.string;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ReadPlainFile {

    private static final String TEST_DIR = "/home/netto/Workspace/Java/Testing/src/main/resources/testfiles/";
    private static final String TEST_FILE = TEST_DIR+ "lista.txt";
    private static final String SCANNER_FILE = TEST_DIR+ "stats.txt";
    private static final String SCANNER_FILE2 = TEST_DIR+ "stats2.txt";

    public static void main(String[] args) {
        try {
//            readAndKeepInMemory();

//            scannerForComplexFile();
//            scannerForComplexFileBr();

            testMethod();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * If your file is small enough to fit in memory, this is the most concise way.
     * It returns a List<String>, where each element is one line from the file.
     * @throws IOException
     */
    protected static void readAndKeepInMemory() throws IOException {

        Path path = Path.of(TEST_FILE);
        List<String> lines = Files.readAllLines(path);
        lines.forEach( linea -> {
            System.out.println("Linea: " + linea);
        });
    }


    /**
     * Before Java 8, this was the standard. It’s still useful if you need fine-grained control or are working in a legacy environment.
     * @throws FileNotFoundException
     */
    protected static void standardBuffered() throws FileNotFoundException {
        try(BufferedReader br = new BufferedReader(new FileReader(TEST_FILE))){
            String linea;
            while( (linea = br.readLine()) != null ){
                System.out.println("Linea: " + linea);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * SCANNER. Scanner is generally slower than BufferedReader or Files.lines() because it does a lot of overhead work (like Regex matching) to figure out if a word is an Integer or a String.
     * When your Java program runs, the Scanner treats the file like a stream of "tokens" (individual pieces of text or numbers).
     */
    protected static void scannerForComplexFile() {
        try (Scanner scanner = new Scanner(new File(SCANNER_FILE))) {
            while (scanner.hasNext()) {

                if (scanner.hasNextInt()) {//searchs for Int
                    int score = scanner.nextInt();
                    System.out.println("Found a score: " + score);
                } else {
                    // Skip strings like "Player" or "Score"
                    scanner.next();
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        }
    }


    /**
     * The power of Scanner lies in its ability to "tokenise" these different types automatically.
     */
    protected static void scannerForComplexFileBr() {
        File file = new File(SCANNER_FILE2);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                // Check if the next token is an integer
                if (scanner.hasNextInt()) {
                    int score = scanner.nextInt();
                    System.out.println("Found Integer: " + score);
                }
                // Check if the next token is a double (like 0.88)
                else if (scanner.hasNextDouble()) {
                    double accuracy = scanner.nextDouble();
                    System.out.println("Found Double: " + accuracy);
                }
                // Otherwise, treat it as a String
                else {
                    String word = scanner.next();
                    System.out.println("Found String: " + word);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found! Make sure "+SCANNER_FILE2+" is in the root folder.");
        }
    }


    public static void testMethod() throws IOException {

//        Path path = Path.of(TEST_FILE);
//        List<String> lsLines = Files.readAllLines(path);
//        lsLines.forEach(linea -> {
//            System.out.println("Linea: " + linea);
//        });


       try(BufferedReader reader = new BufferedReader( new FileReader(TEST_FILE))){
           String linea;
           while( (linea= reader.readLine()) !=null ){
               System.out.println(linea);
           }
       }

    }


}
