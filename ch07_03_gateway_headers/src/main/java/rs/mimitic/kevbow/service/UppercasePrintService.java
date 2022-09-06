package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import rs.mimitic.kevbow.model.Person;

/**
 * Created by mimitic at 2022/Jan/27
 */
@Service
public class UppercasePrintService {

    @ServiceActivator(inputChannel = "uppercaseChannel")
    public String execute(final Person person) {
        return person.getFirstName().concat(" ").concat(person.getLastName()).toUpperCase();
    }
}
