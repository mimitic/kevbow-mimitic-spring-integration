package rs.mimitic.kevbow.service;

import org.springframework.core.annotation.Order;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/17
 */
@Service
public class UppercasePrinterService {

    // service activator as a type of an endpoint
    @ServiceActivator(inputChannel = "inputChannel")
    @Order(2)
    public void print(final Message<String> message) {
        System.out.println(message.getPayload().toUpperCase());
    }
}
