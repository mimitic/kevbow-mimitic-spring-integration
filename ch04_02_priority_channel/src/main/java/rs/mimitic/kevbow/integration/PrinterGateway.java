package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

import java.util.concurrent.Future;

/**
 * Created by mimitic at 2022/Jan/16
 *
 * A message will be passed to this gateway, and a gateway is going
 * to send a message to a channel.
 */
@MessagingGateway(defaultRequestChannel = "inputChannel")
public interface PrinterGateway {

    // accepts and returns a message
    Future<Message<String>> print(Message<String> message);
}
