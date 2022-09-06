package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

/**
 * Created by mimitic at 2022/Mar/01
 */
@MessagingGateway
public interface SimpleGateway {

    @Gateway(requestChannel = "httpOutboundChannel")
    String execute(String message);
}
