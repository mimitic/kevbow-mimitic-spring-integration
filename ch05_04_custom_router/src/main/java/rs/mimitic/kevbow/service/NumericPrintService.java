package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/17
 */
@Service
public class NumericPrintService {

    // service activator as a type of an endpoint
    @ServiceActivator(inputChannel = "intChannel")
    public void print(final Message<?> message) {
        System.out.println("Printing the numeric: " + message.getPayload());
    }
}
