package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.MessagingGateway;

/**
 * Created by mimitic at 2022/Jan/15
 */
@MessagingGateway(defaultRequestChannel = "inputChannel")
public interface CustomGateway {

    void print(String message);
}
