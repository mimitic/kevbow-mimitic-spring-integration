package rs.mimitic.kevbow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.jdbc.JdbcOutboundGateway;
import org.springframework.messaging.MessageChannel;
import rs.mimitic.kevbow.integration.PersonGateway;

import javax.sql.DataSource;
import java.util.Map;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh1003DemoApplication implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersonGateway personGateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh1003DemoApplication.class, args);
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
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "inboundChannel")
    public Object jdbcOutboundGateway() {
        final String updateQuery = "insert into person (person_id, first_name, last_name) values (:payload[id], :payload[firstName], :payload[lastName])";
        final String selectQuery = "select * from person where person_id = :payload[id]";
        final JdbcOutboundGateway outboundGateway = new JdbcOutboundGateway(this.dataSource, updateQuery, selectQuery);
        outboundGateway.setOutputChannelName("jdbcChannel");
        return outboundGateway;
    }
}
