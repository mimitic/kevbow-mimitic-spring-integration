package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import rs.mimitic.kevbow.integration.PrinterGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0402DemoApplication implements ApplicationRunner {

    @Autowired
    private PrinterGateway printerGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0402DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        final List<Future<Message<String>>> futuresOfMessages = new ArrayList<>();

        IntStream.range(0, 10).forEach(i -> {
            final Message<String> message = MessageBuilder
                    .withPayload("Printing message payload for " + i)
                    .setHeader("messageNumber", i)
                    //.setPriority(i)
                    .build();
            System.out.println("Sending message " + i);
            futuresOfMessages.add(this.printerGateway.print(message));  // adding the returned future from the gateway to this list
        });

        for (Future<Message<String>> messageFuture : futuresOfMessages) {
            System.out.println(messageFuture.get().getPayload());
        }
    }

    @Bean
    public PriorityChannel inputChannel() {
        return new PriorityChannel(10, (Message<?> message, Message<?> anotherMessage) -> {
            final String payload = (String) message.getPayload();
            final String anotherPayload = (String) anotherMessage.getPayload();
            final boolean isPayloadEven = Integer.parseInt(payload.substring(payload.length() - 1)) % 2 == 0;
            final boolean isAnotherPayloadEven = Integer.parseInt(anotherPayload.substring(anotherPayload.length() - 1)) % 2 == 0;
            if ( (isPayloadEven && isAnotherPayloadEven) || (!isPayloadEven && !isAnotherPayloadEven) ) {
                return 0;
            } else if (isPayloadEven) {
                return -1;
            } else {
                return 1;
            }
        });
    }
}
