package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

/**
 * Created by mimitic at 2022/Feb/20
 */
@MessagingGateway
public interface FileWriterGateway {

    @Gateway(requestChannel = "ftpChannel")
    void write(@Headers Map<String, String> headers, @Payload String message);
}
