package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/21
 */
@Service
public class DefaultService {

    @ServiceActivator(inputChannel = "defaultChannel")
    public void print(final Message<?> message) {
        System.out.println("Printing from the default channel - " + message.getPayload());
    }
}
