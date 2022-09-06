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

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0304DemoApplication implements ApplicationRunner {

    @Autowired
    private DirectChannel inputChannel;

    @Autowired
    private DirectChannel outputChannel;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0304DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        this.outputChannel.subscribe(message -> System.out.println(message.getPayload()));

        final Message<String> message = MessageBuilder
                .withPayload("Hi there, from the builder pattern.")
                .setHeader("newHeader", "newHeaderValue")
                .build();

        this.inputChannel.send(message);
    }

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel outputChannel() {
        return new DirectChannel();
    }
}
