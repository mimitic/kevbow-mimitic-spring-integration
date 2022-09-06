package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import rs.mimitic.kevbow.service.PrintService;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0302DemoApplication implements ApplicationRunner {

    @Autowired
    private PrintService printService;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0302DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //final Map<String, Object> mapHeader = new HashMap<>();
        //mapHeader.put("key", "value");
        //final MessageHeaders headers = new MessageHeaders(mapHeader);

        //final Message<String> message = new GenericMessage("Hi there!", headers);

        // easier to use builder patter:
        final Message<String> message = MessageBuilder
                .withPayload("Hi there, from the builder pattern.")
                .setHeader("newHeader", "newHeaderValue")
                .build();
        printService.print(message);
    }
}
