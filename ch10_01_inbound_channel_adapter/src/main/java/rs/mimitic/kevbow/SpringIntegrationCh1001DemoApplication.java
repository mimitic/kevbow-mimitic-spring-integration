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
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter;
import org.springframework.messaging.MessageChannel;

import javax.sql.DataSource;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh1001DemoApplication implements ApplicationRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh1001DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

    }

    @Bean
    public MessageChannel jdbcChannel() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(channel = "jdbcChannel", poller = @Poller(fixedRate = "4000"))
    public MessageSource<?> jdbcInboundAdapter() {
        return new JdbcPollingChannelAdapter(this.dataSource, "select * from person");
    }
}
