package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/15
 */
@Service
public class PrintService {

    // service activator as a type of an endpoint
    @ServiceActivator(
            inputChannel = "inputChannel",
            poller = @Poller(fixedRate = "5000", maxMessagesPerPoll = "2")  // polls the channel every 5 seconds
    )
    public Message<String> print(final Message<String> message) {

        System.out.println(message.getPayload());
        final int messageNumber = (int) message.getHeaders().get("messageNumber");
        return MessageBuilder.withPayload("Sending a reply for message: " + messageNumber).build();
    }
}
