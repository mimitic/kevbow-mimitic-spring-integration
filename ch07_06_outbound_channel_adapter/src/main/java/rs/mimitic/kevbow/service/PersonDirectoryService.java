package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.stereotype.Service;
import rs.mimitic.kevbow.model.Person;

/**
 * Created by mimitic at 2022/Jan/28
 */
@Service
public class PersonDirectoryService {

    @InboundChannelAdapter(channel = "registerChannel", poller = @Poller(fixedRate = "3000"))
    public Person findNewPeople() {
        System.out.println("Inbound Adapter has polled this service.");
        return new Person("Tarzan", "Tarzanija");
    }
}
