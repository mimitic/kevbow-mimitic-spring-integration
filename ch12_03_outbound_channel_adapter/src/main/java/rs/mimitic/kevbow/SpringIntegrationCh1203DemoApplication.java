package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.integration.http.inbound.RequestMapping;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.MessageChannel;
import rs.mimitic.kevbow.integration.SimpleGateway;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh1203DemoApplication implements ApplicationRunner {

    @Autowired
    private SimpleGateway simpleGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh1203DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        this.simpleGateway.execute("Anything.");
    }

    @Bean
    public MessageChannel httpChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel httpOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "httpOutboundChannel")
    public HttpRequestExecutingMessageHandler httpOutboundAdapter() {
        final HttpRequestExecutingMessageHandler httpRequestExecutingMessageHandler = new HttpRequestExecutingMessageHandler("http://localhost:8080");
        httpRequestExecutingMessageHandler.setHttpMethod(HttpMethod.GET);
        return httpRequestExecutingMessageHandler;
    }

    @Bean
    public HttpRequestHandlingMessagingGateway httpInboundAdapter() {
        final HttpRequestHandlingMessagingGateway httpRequestHandlingMessagingGateway = new HttpRequestHandlingMessagingGateway();
        httpRequestHandlingMessagingGateway.setRequestChannel(httpChannel());
        httpRequestHandlingMessagingGateway.setRequestMapping(requestMapping());
        return httpRequestHandlingMessagingGateway;
    }

    @Bean
    public RequestMapping requestMapping() {
        final RequestMapping requestMapping = new RequestMapping();
        requestMapping.setMethods(HttpMethod.GET);
        requestMapping.setPathPatterns("/");
        return requestMapping;
    }
}
