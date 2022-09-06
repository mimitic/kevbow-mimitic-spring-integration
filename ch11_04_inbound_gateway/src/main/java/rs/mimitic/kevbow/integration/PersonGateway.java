package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import rs.mimitic.kevbow.model.Person;

/**
 * Created by mimitic at 2022/Feb/27
 */
@MessagingGateway
public interface PersonGateway {

    @Gateway(requestChannel = "inboundChannel")
    void save(Person person);
}
