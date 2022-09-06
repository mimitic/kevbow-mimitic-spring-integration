package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import rs.mimitic.kevbow.integration.PrinterGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0508DemoApplication implements ApplicationRunner {

    @Autowired
    private PrinterGateway printerGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0508DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        IntStream.range(0, 10).forEach(i -> {
            final Message<?> message = MessageBuilder.withPayload("Milos Mitic " + i).build();
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
    public MessageChannel aggregatorChannel() {
        return new DirectChannel();
    }

    @Splitter(inputChannel = "inputChannel", outputChannel = "aggregatorChannel")
    public List<String> split(final Message<?> message) {
        return new ArrayList<>(Arrays.asList(message.getPayload().toString().split(" ")));
    }

    @Aggregator(inputChannel = "aggregatorChannel", outputChannel = "outputChannel")
    public String aggregator(final List<String> messagesParts) {
        final StringBuilder sb = new StringBuilder("[ ".concat(messagesParts.get(0)));
        for (int i = 1; i < messagesParts.size(); i++) {
            sb.append(" ".concat(messagesParts.get(i)));
        }
        sb.append(" ]");
        return sb.toString();
    }
}
