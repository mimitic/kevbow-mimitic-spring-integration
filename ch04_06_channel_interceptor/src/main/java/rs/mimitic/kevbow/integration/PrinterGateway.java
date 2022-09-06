package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

/**
 * Created by mimitic at 2022/Jan/16
 */
@MessagingGateway(defaultRequestChannel = "pollableChannel")
public interface PrinterGateway {

    void print(Message<?> message);
}
