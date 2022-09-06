package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import rs.mimitic.kevbow.integration.PrinterGateway;
import rs.mimitic.kevbow.model.Person;

import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0603DemoApplication implements ApplicationRunner {

    @Autowired
    private PrinterGateway printerGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0603DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        final Person[] payloads = { new Person("Milos", "Mitic"), new Person("Kevin", "Bowersox"), new Person("Jane", "Doe") };
        IntStream.range(0, payloads.length).forEach(i -> {
            final Message<?> message = MessageBuilder.withPayload(payloads[i]).build();
            this.printerGateway.print(message);
        });
    }

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public ObjectToStringTransformer transformer() {
        return new ObjectToStringTransformer();
    }
}
