package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

/**
 * Created by mimitic at 2022/Jan/16
 */
@MessagingGateway(defaultRequestChannel = "inputChannel")
public interface PrinterGateway {

    void print(Message<String> message);
}
