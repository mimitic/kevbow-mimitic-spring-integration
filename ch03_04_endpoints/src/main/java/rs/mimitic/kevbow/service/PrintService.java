package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/15
 */
@Service
public class PrintService {

    // service activator as a type of an endpoint
    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public Message<String> print(final Message<String> message) {

        final MessageHeaders headers = message.getHeaders();
        headers.entrySet().forEach(entry ->
            System.out.println("Header's key: ".concat(entry.getKey()).concat(", Header's value: ".concat(entry.getValue().toString()))));

        System.out.println(message.getPayload());

        return MessageBuilder.withPayload("New Message").build();
    }
}
