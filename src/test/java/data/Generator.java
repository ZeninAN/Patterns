package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class Generator {
    private Generator() {

    }

    private static Random random = new Random();


    public static RegistrationInfo generateRequestCard(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return new RegistrationInfo(faker.address().cityName(),
                faker.name().lastName() + " " + faker.name().firstName(),faker.phoneNumber().phoneNumber());
    }

    public static String generateDate(int shift, int range) {
        return LocalDate.now().plusDays(3 + shift).plusDays(random.nextInt(range))
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
