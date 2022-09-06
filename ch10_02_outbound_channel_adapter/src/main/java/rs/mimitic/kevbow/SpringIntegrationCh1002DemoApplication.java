package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import rs.mimitic.kevbow.integration.PersonGateway;

import javax.sql.DataSource;
import java.util.Map;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh1002DemoApplication implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersonGateway personGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh1002DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        this.personGateway.savePerson(Map.of("id", "3", "firstName", "Gurban", "lastName", "Gurbanov"));
    }

    @Bean
    public MessageChannel jdbcChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(channel = "jdbcChannel", poller = @Poller(fixedRate = "4000"))
    public MessageSource<?> jdbcInboundAdapter() {
        return new JdbcPollingChannelAdapter(this.dataSource, "select * from person");
    }

    @Bean
    @ServiceActivator(inputChannel = "outboundChannel")
    public MessageHandler jdbcOutboundAdapter() {
        return new JdbcMessageHandler(this.dataSource, "insert into person (person_id, first_name, last_name)" +
                " values (:payload[id], :payload[firstName], :payload[lastName])");
    }
}
