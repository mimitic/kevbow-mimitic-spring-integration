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
    public String print(final Message<?> message) {
        System.out.println("Result: ".concat(message.toString()));
        return "From the inbound gateway... Payload result: ".concat(message.getPayload().toString());
    }

    @ServiceActivator(inputChannel = "replyChannel")
    public void printConsole(final Message<?> message) {
        System.out.println("Message was passed to console from outbound gateway - Message: ".concat(message.toString()));
    }
}
