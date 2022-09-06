package rs.mimitic.kevbow;

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
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import java.io.File;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0802DemoApplication implements ApplicationRunner {

    private static final String INPUT_DIR = "/home/mimitic/Documents/intellij_idea_ws/kevbow-mimitic-spring-integration/ch08_02_write_file/src/main/resources/read/";  // resource dir
    private static final String FILE_PATTERN = "sample.txt";

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0802DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

    }

    @Bean
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(channel = "inboundChannel", poller = @Poller(fixedRate = "4000"))
    public FileReadingMessageSource fileReadingMessageSource() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(INPUT_DIR));
        messageSource.setFilter(new SimplePatternFileListFilter(FILE_PATTERN));
        return messageSource;
    }

    @Bean
    @ServiceActivator(inputChannel = "inboundChannel")
    public MessageHandler fileWritingMessageHandler() {
        final String WRITE_DIR = "/home/mimitic/Documents/intellij_idea_ws/kevbow-mimitic-spring-integration/ch08_02_write_file/src/main/resources/write/";
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File(WRITE_DIR));
        handler.setFileExistsMode(FileExistsMode.REPLACE);
        handler.setExpectReply(false);
        return handler;
    }
}
