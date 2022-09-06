package rs.mimitic.kevbow.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/15
 */
@Service
public class PrintService {

    public void print(final Message<String> message) {

        final MessageHeaders headers = message.getHeaders();
        headers.entrySet().forEach(entry ->
            System.out.println("Header's key: ".concat(entry.getKey()).concat(", Header's value: ".concat(entry.getValue().toString()))));

        System.out.println(message.getPayload());
    }
}
