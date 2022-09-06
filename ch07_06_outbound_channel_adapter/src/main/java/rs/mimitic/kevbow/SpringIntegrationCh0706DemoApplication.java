package rs.mimitic.kevbow;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0706DemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0706DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

    }

    @Bean
    public MessageChannel registerChannel() {
        return new DirectChannel();
    }
}
