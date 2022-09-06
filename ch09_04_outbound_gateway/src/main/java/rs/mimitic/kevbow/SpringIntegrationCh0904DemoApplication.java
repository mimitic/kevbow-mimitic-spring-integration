package rs.mimitic.kevbow;

import org.apache.commons.net.ftp.FTPFile;
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
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.ftp.gateway.FtpOutboundGateway;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import rs.mimitic.kevbow.integration.FileWriterGateway;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0904DemoApplication implements ApplicationRunner {

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.pswd}")
    private String ftpPswd;

    @Autowired
    private FileWriterGateway gateway;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0904DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        this.gateway.read("/Documents/ftp-sample.txt");
    }

    @Bean
    public MessageChannel ftpChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel transformChannel() {
        return new DirectChannel();
    }

    @Bean
    public SessionFactory<FTPFile> ftpClientSessionFactory() {
        final DefaultFtpSessionFactory factory = new DefaultFtpSessionFactory();
        factory.setHost("localhost");
        factory.setUsername(ftpUsername);
        factory.setPassword(ftpPswd);
        factory.setPort(21);
        return new CachingSessionFactory<>(factory);
    }

    @Bean
    @ServiceActivator(inputChannel = "ftpChannel")
    public MessageHandler ftpOutboundGateway() {
        final FtpOutboundGateway outboundGateway =
                new FtpOutboundGateway(ftpClientSessionFactory(), "get", "payload");
        outboundGateway.setLocalDirectoryExpressionString("'ch09_04_outbound_gateway/src/main/resources/write/'");
        outboundGateway.setOutputChannelName("outboundChannel");
        return outboundGateway;
    }

    @Transformer(inputChannel = "outboundChannel", outputChannel = "transformChannel")
    public FileToStringTransformer fileToStringTransformer() {
        return new FileToStringTransformer();
    }
}
