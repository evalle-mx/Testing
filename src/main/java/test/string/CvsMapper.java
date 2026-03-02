package test.string;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CvsMapper {

    // Record that will hold the values of each row (Immutable data carrier)
    public record Employee(int id, String name, String department, double salary) {}

    private static final String empCsvFile = "/home/netto/Workspace/Java/Testing/src/main/resources/testfiles/employees.csv";

    public static void main(String[] args) {
        parseEmployeesCsv();
    }

    protected static void parseEmployeesCsv(){
        Path path = Path.of(empCsvFile);
        List<Employee> lsEmployee = null;
        try (Stream<String> lines = Files.lines(path) ) { //Files.lines(path).skip(1)  for HEADERS
            lsEmployee = lines
                    .map( linea -> linea.split(",")) //Split the line by separator => tokens
                    .map( token -> new Employee(
                            Integer.parseInt(token[0].trim()),
                            token[1].trim(),
                            token[2].trim(),
                            Double.parseDouble(token[3].trim())
                    ) )
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(lsEmployee!= null && !lsEmployee.isEmpty() ){
            lsEmployee.forEach(System.out::println);

            System.out.println("Just the names:");
            List<String> namesOnly = lsEmployee.stream().map( emp1 -> emp1.name).toList();
            System.out.println(namesOnly);

    /* PERFORMANCE: Filters should be placed as early as possible in the stream pipeline to reduce the number
    of elements processed by subsequent operations (like heavy mapping or sorting) */
            //FILTER for employees than earn more than 80,000
            List<Employee> lsGoodPaidEmps = lsEmployee.stream()
                    .filter( emp -> emp.salary >= 75000)  //
                    .toList();
            System.out.println("Good paid Employees:");
            lsGoodPaidEmps.forEach(System.out::println );

        }else{
            System.out.println("No EMPLOYEES on File");
        }
    }
}
