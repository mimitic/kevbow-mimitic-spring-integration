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
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.jms.*;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.AbstractMessageListenerContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import rs.mimitic.kevbow.integration.PersonGateway;
import rs.mimitic.kevbow.model.Person;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh1103DemoApplication implements ApplicationRunner {

    @Value("${spring.activemq.broker-url}")
    private String activemqBrokerUrl;

    @Autowired
    private PersonGateway personGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh1103DemoApplication.class, args);
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
    public JmsTemplate jmsTemplate() {
        final JmsTemplate jmsTemplate = new DynamicJmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        return jmsTemplate;
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
        final JmsSendingMessageHandler jmsSendingMessageHandler = new JmsSendingMessageHandler(jmsTemplate());
        jmsSendingMessageHandler.setDestination(activeMQQueue());
        return jmsSendingMessageHandler;
    }

    @Bean
    public JmsMessageDrivenEndpoint jmsMessageDrivenEndpoint() {
        return new JmsMessageDrivenEndpoint(jmsMessageListenerContainer(), jmsMessageDrivenListener());
    }

    @Bean
    public AbstractMessageListenerContainer jmsMessageListenerContainer() {
        final AbstractMessageListenerContainer listenerContainer = new DefaultMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory());
        listenerContainer.setDestination(activeMQQueue());
        return listenerContainer;
    }

    @Bean
    public ChannelPublishingJmsMessageListener jmsMessageDrivenListener() {
        final ChannelPublishingJmsMessageListener jmsMessageListener = new ChannelPublishingJmsMessageListener();
        jmsMessageListener.setRequestChannel(jmsChannel());
        return jmsMessageListener;
    }
}
