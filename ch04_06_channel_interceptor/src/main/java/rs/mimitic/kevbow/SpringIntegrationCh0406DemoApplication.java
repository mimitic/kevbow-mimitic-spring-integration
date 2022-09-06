package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeTo;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import rs.mimitic.kevbow.integration.CustomChannelInterceptor;
import rs.mimitic.kevbow.integration.PrinterGateway;

import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0406DemoApplication implements ApplicationRunner {

    @Autowired
    private PrinterGateway printerGateway;

    @Autowired
    private CustomChannelInterceptor customChannelInterceptor;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0406DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

        IntStream.range(0, 10).forEach(i -> {
            final Message<?> message = MessageBuilder
                    .withPayload("Printing message payload for " + i)
                    .build();
            this.printerGateway.print(message);
        });
    }

    @Bean
    public PublishSubscribeChannel inputChannel() {
        return new PublishSubscribeChannel(Executors.newFixedThreadPool(5));
    }

    // pollable channel will be placed between the gateway and subscribable channel
    @Bean
    @BridgeTo(value = "inputChannel", poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "2"))
    public PollableChannel pollableChannel() {
        final QueueChannel channel = new QueueChannel(10);
        channel.addInterceptor(customChannelInterceptor);
        return channel;
    }
}
