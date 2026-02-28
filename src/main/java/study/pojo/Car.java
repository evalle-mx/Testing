package study.pojo;

/**
 * A record automatically manages "boilerplate" code: constructors, getters, equals(), hashCode(), and toString().
 * ADVANTAGES: Conciseness (clean and readable), Immutability, Intent (People knows Contains data),  Automatic Maintenance (At new field, java automatically updates the equals() and toString())
 * DISADVANTAGES: No State Change (No SETTERs), No Inheritance (not extend of being extended)
 * USAGE: Use records for DTOs (Data Transfer Objects), API responses, or simple keys in a Map.
 * @param type
 * @param make
 * @param model
 * @param engineCapacity
 */
public record Car(String type, String make, String model, Integer engineCapacity) {
}
