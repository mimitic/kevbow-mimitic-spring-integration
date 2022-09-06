package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import rs.mimitic.kevbow.model.Person;

/**
 * Created by mimitic at 2022/Jan/27
 */
@MessagingGateway
public interface EnhancedPrinterGateway {

    @Gateway(requestChannel = "printChannel")
    void print(final Person person);

    @Gateway(requestChannel = "uppercaseChannel")
    String uppercase(final Person person);
}
