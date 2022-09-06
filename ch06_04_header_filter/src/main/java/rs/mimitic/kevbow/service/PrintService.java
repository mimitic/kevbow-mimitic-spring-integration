package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/15
 */
@Service
public class PrintService {

    // service activator as a type of an endpoint
    @ServiceActivator(inputChannel = "outputChannel")
    public void print(final Message<?> message) {
        System.out.println(message.getPayload());
        message.getHeaders().entrySet().forEach(entry -> {
            System.out.println("Header: " + entry.getKey() + ", Value: " + entry.getValue());
        });
    }
}
