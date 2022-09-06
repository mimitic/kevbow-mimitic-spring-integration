package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import rs.mimitic.kevbow.integration.PrinterGateway;

import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0403DemoApplication implements ApplicationRunner {

    @Autowired
    private PrinterGateway printerGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0403DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        IntStream.range(0, 10).forEach(i -> {
            final Message<String> message = MessageBuilder
                    .withPayload("Printing message payload for " + i)
                    .build();
            this.printerGateway.print(message);
        });
    }

    @Bean
    public DirectChannel inputChannel() {
        final DirectChannel directChannel = new DirectChannel();
        //directChannel.setFailover(false);
        return directChannel;
    }
}
