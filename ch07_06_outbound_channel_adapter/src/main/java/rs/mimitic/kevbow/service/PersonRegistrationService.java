package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import rs.mimitic.kevbow.model.Person;

/**
 * Created by mimitic at 2022/Jan/28
 */
@Service
public class PersonRegistrationService {

    @ServiceActivator(inputChannel = "registerChannel")
    public void registerEmail(final Person person) {
        System.out.println("Email created for: " + person.toString());
    }
}
