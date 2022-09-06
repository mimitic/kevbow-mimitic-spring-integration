package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Feb/27
 */
@Service
public class MessagePrinterService {

    @ServiceActivator(inputChannel = "httpChannel")
    public String print(final Message<?> message) {
        return "From the inbound gateway - message: ".concat(message.toString());
    }

    //@ServiceActivator(inputChannel = "httpChannel")
    public void printConsole(final Message<?> message) {
        System.out.println("Message was passed to printConsole method");
        System.out.println(message);
    }
}
