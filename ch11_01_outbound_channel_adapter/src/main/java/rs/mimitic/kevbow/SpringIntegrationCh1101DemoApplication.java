package rs.mimitic.kevbow;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import rs.mimitic.kevbow.integration.PersonGateway;
import rs.mimitic.kevbow.model.Person;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh1101DemoApplication implements ApplicationRunner {

    @Value("${spring.activemq.broker-url}")
    private String activemqBrokerUrl;

    @Autowired
    private PersonGateway personGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh1101DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        this.personGateway.save(new Person(3, "John", "Doe"));
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(activemqBrokerUrl);
        return new CachingConnectionFactory(connectionFactory);
    }

    @Bean
    public MessageChannel jmsChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "inboundChannel", outputChannel = "outboundChannel")
    public ObjectToStringTransformer objectToStringTransformer() {
        return new ObjectToStringTransformer();
    }

    @Bean
    public Queue activeMQQueue() {
        return new ActiveMQQueue("sample.queue");
    }

    @Bean
    @ServiceActivator(inputChannel = "outboundChannel")
    public MessageHandler jmsMessageHandler() {
        final JmsSendingMessageHandler jmsSendingMessageHandler = new JmsSendingMessageHandler(new JmsTemplate(connectionFactory()));
        jmsSendingMessageHandler.setDestination(activeMQQueue());
        return jmsSendingMessageHandler;
    }
}
