package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.util.concurrent.ListenableFuture;
import rs.mimitic.kevbow.model.Person;

/**
 * Created by mimitic at 2022/Jan/27
 */
@MessagingGateway(defaultHeaders = @GatewayHeader(name = "globalHeader", expression = "#args[0].firstName"))
public interface EnhancedPrinterGateway {

    @Gateway(requestChannel = "printChannel")
    void print(final Person person);

    @Gateway(requestChannel = "uppercaseChannel")
    ListenableFuture<String> uppercase(final Person person);
}
