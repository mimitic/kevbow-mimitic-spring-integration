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
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.integration.router.MessageRouter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import rs.mimitic.kevbow.integration.PrinterGateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0504DemoApplication implements ApplicationRunner {

    @Autowired
    private PrinterGateway printerGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0504DemoApplication.class, args);
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
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel intChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel stringChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel defaultChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "inputChannel")
    public MessageRouter customRouter() {
        return new AbstractMessageRouter() {
            @Override
            protected Collection<MessageChannel> determineTargetChannels(final Message<?> message) {
                Collection<MessageChannel> targetChannels = new ArrayList<>();
                if (Integer.class.equals(message.getPayload().getClass())) {
                    targetChannels.add(intChannel());
                } else if (String.class.equals(message.getPayload().getClass())) {
                    targetChannels.add(stringChannel());
                }
                return targetChannels;
            }
        };
    }
}
