package rs.mimitic.kevbow;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.integration.http.inbound.RequestMapping;
import org.springframework.messaging.MessageChannel;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh1201DemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh1201DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
    }

    @Bean
    public MessageChannel httpChannel() {
        return new DirectChannel();
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
