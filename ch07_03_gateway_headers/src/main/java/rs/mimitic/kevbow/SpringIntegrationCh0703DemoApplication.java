package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import rs.mimitic.kevbow.integration.EnhancedPrinterGateway;
import rs.mimitic.kevbow.model.Person;

import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0703DemoApplication implements ApplicationRunner {

    @Autowired
    private EnhancedPrinterGateway enhancedPrinterGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0703DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        final Person[] payloads = { new Person("Milos", "Mitic"), new Person("Kevin", "Bowersox"), new Person("Jane", "Doe") };
        IntStream.range(0, payloads.length).forEach(i -> {
            if (i % 2 == 0) {
                this.enhancedPrinterGateway.print(payloads[i]);
            } else {
                final String uppercased = this.enhancedPrinterGateway.uppercase(payloads[i]);
                System.out.println(uppercased);
            }
            System.out.println("************************");
        });
    }

    @Bean
    public MessageChannel printChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel uppercaseChannel() {
        return new DirectChannel();
    }
}
