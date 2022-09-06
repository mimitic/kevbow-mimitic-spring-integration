package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.Map;

/**
 * Created by mimitic at 2022/Feb/23
 */
@MessagingGateway
public interface PersonGateway {

    @Gateway(requestChannel = "inboundChannel")
    void savePerson(Map<?,?> personData);
}
