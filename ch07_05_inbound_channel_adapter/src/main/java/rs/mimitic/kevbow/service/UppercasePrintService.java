package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import rs.mimitic.kevbow.model.Person;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by mimitic at 2022/Jan/27
 */
@Service
public class UppercasePrintService {

    @ServiceActivator(inputChannel = "uppercaseChannel")
    public String execute(final Person person) {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        return person.getFirstName().concat(" ").concat(person.getLastName()).toUpperCase();
    }
}
