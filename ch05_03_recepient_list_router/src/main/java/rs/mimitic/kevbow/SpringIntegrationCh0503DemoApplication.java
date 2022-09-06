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
import org.springframework.integration.router.RecipientListRouter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import rs.mimitic.kevbow.integration.PrinterGateway;

import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0503DemoApplication implements ApplicationRunner {

    @Autowired
    private PrinterGateway printerGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0503DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        IntStream.range(0, 10).forEach(i -> {
            final Message<?> message;
            if (i % 2 == 0) {
                message = MessageBuilder.withPayload("Printing message payload for " + i).build();
            } else {
                message = MessageBuilder.withPayload(i).build();
            }
            this.printerGateway.print(message);
        });
    }

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel defaultChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "inputChannel")
    public RecipientListRouter recipientListRouter() {
        final RecipientListRouter router = new RecipientListRouter();
        router.setDefaultOutputChannelName("defaultChannel");
        router.addRecipient("intChannel", "payload.equals(5)");
        router.addRecipient("stringChannel", "payload.equals(51)");
        return router;
    }
}
