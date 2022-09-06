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
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.HeaderFilter;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import rs.mimitic.kevbow.integration.CustomHeaderEnricher;
import rs.mimitic.kevbow.integration.PrinterGateway;
import rs.mimitic.kevbow.model.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0605DemoApplication implements ApplicationRunner {

    @Autowired
    private PrinterGateway printerGateway;

    @Autowired
    private CustomHeaderEnricher headerEnricher;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0605DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        final Person[] payloads = { new Person("Milos", "Mitic"), new Person("Kevin", "Bowersox"), new Person("Jane", "Doe") };
        IntStream.range(0, payloads.length).forEach(i -> {
            final Message<?> message = MessageBuilder
                    .withPayload(payloads[i])
                    .setHeader("privateKey", "123456")  // header under-test, to be removed
                    .build();
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
    public MessageChannel filterChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel enricherChannel() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "filterChannel", outputChannel = "outputChannel")
    public ObjectToStringTransformer transformer() {
        return new ObjectToStringTransformer();
    }

    @Bean
    @Transformer(inputChannel = "enricherChannel", outputChannel = "filterChannel")
    public HeaderFilter filter() {
        return new HeaderFilter("privateKey");  // header with key=privateKey that will be removed
    }

    @Bean
    @Transformer(inputChannel = "inputChannel", outputChannel = "enricherChannel")
    public HeaderEnricher headerEnricher() {
        final Map<String, HeaderValueMessageProcessor<?>> headers = new HashMap<>();
        headers.put("publicKey", new StaticHeaderValueMessageProcessor<>("654321"));
        headers.put("anotherHeaderKey", new StaticHeaderValueMessageProcessor<>(this.headerEnricher.getHeaderValue()));
        return new HeaderEnricher(headers);
    }
}
