package rs.mimitic.kevbow;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.AcceptOnceFileListFilter;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ftp.filters.FtpSimplePatternFileListFilter;
import org.springframework.integration.ftp.inbound.FtpInboundFileSynchronizer;
import org.springframework.integration.ftp.inbound.FtpInboundFileSynchronizingMessageSource;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.File;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0902DemoApplication implements ApplicationRunner {

    private static final String WRITE_DIR = "/home/mimitic/Documents/intellij_idea_ws/kevbow-mimitic-spring-integration/ch09_02_inbound_adapter/src/main/resources/write/";
    private static final String FILE_PATTERN = "sample.txt";

    @Value("${ftp.username}")
    private String ftpUsername;

    @Value("${ftp.pswd}")
    private String ftpPswd;

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0902DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

    }

    @Bean
    public MessageChannel ftpInboundChannel() {
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
    public FtpInboundFileSynchronizer ftpInboundFileSynchronizer() {
        final FtpInboundFileSynchronizer synchronizer = new FtpInboundFileSynchronizer(ftpClientSessionFactory());
        synchronizer.setDeleteRemoteFiles(true);
        synchronizer.setRemoteDirectory("/Documents");
        synchronizer.setFilter(new FtpSimplePatternFileListFilter("*-".concat(FILE_PATTERN)));
        return synchronizer;
    }

    @Bean
    @InboundChannelAdapter(channel = "ftpInboundChannel", poller = @Poller(fixedRate = "5000"))
    public FtpInboundFileSynchronizingMessageSource ftpMessageSource() {
        final FtpInboundFileSynchronizingMessageSource messageSource = new FtpInboundFileSynchronizingMessageSource(ftpInboundFileSynchronizer());
        messageSource.setLocalDirectory(new File(WRITE_DIR));
        messageSource.setAutoCreateLocalDirectory(true);
        messageSource.setLocalFilter(new AcceptOnceFileListFilter<>());
        messageSource.setMaxFetchSize(1);
        return messageSource;
    }

    @Bean
    @ServiceActivator(inputChannel = "ftpInboundChannel")
    public MessageHandler fileWritingMessageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(WRITE_DIR));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        return handler;
    }
}
