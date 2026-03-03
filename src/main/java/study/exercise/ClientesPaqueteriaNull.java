/*
Ejercicio Optionals +Lambdas + Streams

Una empresa de paqueteria posee almacenados los datos de sus diferentes clientes [Numero_cliente, nombre, apellido, direccion, telefono].
Estos datos son utilizados cada vez que el cliente lleva a cabo un nuevo envio.
Sin embargo, hay clientes que poseen el campo de direccion sin completar (null), dado que anteriormente el sistema no lo consideraba obligatorio.

Al buscar una direccion para crear un nuevo envio, puede pasar que la misma No EXISTA.

Actualmente el sistema arroja un error de tipo "NullPointerException" cada vez que se busca un cliente sin direccion

Se desea que el sistema sea capaz de informar cuando un cliente tenga este dato Vacio sin arroar un error/Excepcion

 */
package study.exercise;

//import study.exercise.model.ClientePack;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class ClientesPaqueteriaNull {


    public record ClientePack(Long numCliente, String nombre, String apellido, String direccion, String telefono){  }

    /**
     * MOCK method to obtain a list of Objects (Records, DTO, Model)
     * @param numOfClientes
     * @return
     */
    private static List<ClientePack> getClientes(int numOfClientes) {
        /*
        return  Stream.of(
                new ClientePack(1L, "Juan", "Perez", "Calle 1 #100", "555-0001"),
                new ClientePack(2L, "Maria Antonieta", "Gomez", null, "555-0002"),
                new ClientePack(3L, "Carlos Alberto", "Lopez", null, "555-0003"),
                new ClientePack(4L, "Ana", "Martinez", "Calle 4 #103", null),
                new ClientePack(5L, "Luis Fernando", "Rodriguez", null, "555-0005"),
                new ClientePack(6L, "Laura", "Fernandez", "Calle 6 #105", "555-0006"),
                new ClientePack(7L, "Pedro", "Ramirez", "Calle 7 #106", "555-0007"),
                new ClientePack(8L, "Sofia", "Torres", "Calle 8 #107", "555-0008"),
                new ClientePack(9L, "Miguel", "Flores", "Calle 9 #108", "555-0009"),
                new ClientePack(10L, "Lucia", "Vargas", "Calle 10 #109", "555-0010"),
                new ClientePack(11L, "Diego", "Castro", "Calle 11 #110", "555-0011"),
                new ClientePack(12L, "Valentina", "Morales", null, "555-0012"),
                new ClientePack(13L, "Jorge", "Rojas", "Calle 13 #112", "555-0013"),
                new ClientePack(14L, "Camila", "Ortega", "Calle 14 #113", "555-0014"),
                new ClientePack(15L, "Andres", "Silva", "Calle 15 #114", "555-0015"),
                new ClientePack(16L, "Elena", "Mendoza", "Calle 16 #115", "555-0016"),
                new ClientePack(17L, "Ricardo", "Navarro", null, "555-0017"),
                new ClientePack(18L, "Paula", "Herrera", "Calle 18 #117", "555-0018"),
                new ClientePack(19L, "Daniel", "Gutierrez", "Calle 19 #118", "555-0019"),
                new ClientePack(20L, "Natalia", "Reyes", "Calle 20 #119", "555-0020")
        ).toList(); // */

        return IntStream.rangeClosed(1, numOfClientes)
                .mapToObj(i -> new ClientePack(
                        Long.valueOf(i),
                        "Nombre" + i,
                        "Apellido" + i,
                        (i%2 ==0 || i%5 ==0)?
                                "Calle " + i + " #10" + i: null,
                        "555-00" + String.format("%02d", i)
                ))
                .toList();

        /* Using Java-Faker (net.datafaker) */
//        Faker faker = new Faker();
//
//        return IntStream.rangeClosed(1, numOfClientes)
//                .mapToObj(i -> new ClientePack(
//                        i,
//                        faker.name().firstName(),
//                        faker.name().lastName(),
//                        faker.address().streetAddress(),
//                        faker.phoneNumber().cellPhone()
//                ))
//                .toList();
    }

    public static void main(String[] args) {
        List<ClientePack> lsClientes = getClientes(20);
        lsClientes.forEach(System.out::println);

        // Obtain the input
        System.out.println("Please enter the Client ID you would like to send a package:");
        Scanner sc = new Scanner(System.in);
        Long id = sc.nextLong();

//        String nombre = sc.next();
//        String apellido = sc.next();

        /* Find the Client if exists */
        //ClientePack client = readCliente(id, lsClientes);
        Optional<ClientePack> client = readCliente(id, lsClientes);

        /* if Exists, find the Address */
        if(client.isPresent()) {
            Optional<String> dirCliente = getAddress(client);
            if(dirCliente.isPresent()) {
                System.out.println("Client address is " + dirCliente.get() );
            }else  {
                System.out.println("This Client has no address");
            }
        }
        else  {
            System.out.println("client " + id + " Not found"); // END
        }
    }

    //    private static ClientePack readCliente(Long id, List<ClientePack> lsClientes) {  //Podria regresar NULL
//        return lsClientes.stream()
//                .filter( client -> client.id().equals(id)).
//                findFirst().orElse(null);
//    }
private static Optional<ClientePack> readCliente(Long id, List<ClientePack> lsClientes) {
    return lsClientes.stream()
//            .filter( client -> client.getNro_cliente().equals(id)).
            .filter( client -> client.numCliente().equals(id))
            .findFirst();
}


    private static Optional<String> getAddress(Optional<ClientePack> client) {
        Optional<String> s = client
                //.map(ClientePack::getDireccion);
                .map(ClientePack::direccion );
        return s;
    }



}
