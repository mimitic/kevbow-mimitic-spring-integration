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
import rs.mimitic.kevbow.integration.CustomGateway;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0201DemoApplication implements ApplicationRunner {

    @Autowired
    private CustomGateway customGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0201DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.customGateway.print("Hi there!");
    }

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }
}
