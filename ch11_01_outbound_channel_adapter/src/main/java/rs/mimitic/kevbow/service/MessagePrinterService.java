package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Feb/27
 */
@Service
public class MessagePrinterService {

    @ServiceActivator(inputChannel = "jmsChannel")
    public void print(final Message<?> message) {
        System.out.println("Result: ".concat(message.toString()));
    }
}
