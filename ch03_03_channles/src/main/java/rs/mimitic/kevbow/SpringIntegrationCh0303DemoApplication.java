package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import rs.mimitic.kevbow.service.PrintService;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0303DemoApplication implements ApplicationRunner {

    @Autowired
    private PrintService printService;

    @Autowired
    private DirectChannel messageChannel;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0303DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        this.messageChannel.subscribe(message -> this.printService.print((Message<String>) message));

        // easier to use builder pattern:
        final Message<String> message = MessageBuilder
                .withPayload("Hi there, from the builder pattern.")
                .setHeader("newHeader", "newHeaderValue")
                .build();

        messageChannel.send(message);
    }

    @Bean
    public DirectChannel messageChannel() {
        return new DirectChannel();
    }
}
