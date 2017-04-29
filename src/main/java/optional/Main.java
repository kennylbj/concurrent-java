package optional;

import java.util.Optional;

/**
 * Created by kennylbj on 2017/4/29.
 */
public class Main {

    public static void main(String[] args) {
        // Given a person who may or may not have a car
        // return the hud brand name.
        Optional<Person> person = Optional.of(new Person());
        String brand = person.flatMap(Person::getCar) // A person may or may not have a car
                        .flatMap(Car::getHud)         // A car may or may not have a hud
                        .map(Hud::getBrand)           // A hud must have a brand
                        .orElse("Unknown");
        System.out.println("Hud brand name: " + brand);

        // Given a person and a car
        // find the most suitable hud.
        Optional<Car> car = Optional.of(new Car());
        person.flatMap(p ->
            car.map(c -> {
                // Do some logical operations and find most suitable hud
                System.out.println("Most suitable hud for person: " + p + " and car: " + c);
                return Optional.of(new Hud());
            })
        );

    }


}
