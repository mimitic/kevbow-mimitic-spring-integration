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
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import rs.mimitic.kevbow.integration.EnhancedPrinterGateway;
import rs.mimitic.kevbow.model.Person;

import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0704DemoApplication implements ApplicationRunner {

    @Autowired
    private EnhancedPrinterGateway enhancedPrinterGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0704DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        final Person[] payloads = { new Person("Milos", "Mitic"), new Person("Kevin", "Bowersox"), new Person("Jane", "Doe") };
        IntStream.range(0, payloads.length).forEach(i -> {

            System.out.println("Invoking the gateway method - iteration: " + i);

            final ListenableFuture<String> uppercasedMsg = this.enhancedPrinterGateway.uppercase(payloads[i]);
            uppercasedMsg.addCallback(new ListenableFutureCallback<String>() {
                @Override
                public void onFailure(Throwable ex) {

                }
                @Override
                public void onSuccess(String result) {
                    System.out.println("Invoking the success callback.");
                    System.out.println("Result: " + result);
                }
            });
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
