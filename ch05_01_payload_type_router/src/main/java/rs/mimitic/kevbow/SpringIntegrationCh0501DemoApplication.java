package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.router.PayloadTypeRouter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import rs.mimitic.kevbow.integration.PrinterGateway;

import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0501DemoApplication implements ApplicationRunner {

    @Autowired
    private PrinterGateway printerGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0501DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        IntStream.range(0, 10).forEach(i -> {
            final Message<?> message;
            if (i % 2 == 0) {
                message = MessageBuilder.withPayload("Printing message payload for " + i).build();  // msg of type String
            } else {
                message = MessageBuilder.withPayload(i).build();  // msg of type Integer
            }
            this.printerGateway.print(message);
        });
    }

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "inputChannel")
    public PayloadTypeRouter payloadTypeRouter() {
        final PayloadTypeRouter router = new PayloadTypeRouter();
        router.setChannelMapping(String.class.getName(), "stringChannel");
        router.setChannelMapping(Integer.class.getName(), "intChannel");
        return router;
    }
}
