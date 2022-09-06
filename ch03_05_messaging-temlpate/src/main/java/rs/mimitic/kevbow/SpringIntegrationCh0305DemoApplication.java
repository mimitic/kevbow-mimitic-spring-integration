package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0305DemoApplication implements ApplicationRunner {

    @Autowired
    private DirectChannel inputChannel;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0305DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        final Message<String> message = MessageBuilder
                .withPayload("Hi there, from the builder pattern.")
                .setHeader("newHeader", "newHeaderValue")
                .build();

        final MessagingTemplate messagingTemplate = new MessagingTemplate();
        final Message<?> messageReturned = messagingTemplate.sendAndReceive(inputChannel, message);
        System.out.println("Message returned: " + messageReturned.getPayload());
    }

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }
}
