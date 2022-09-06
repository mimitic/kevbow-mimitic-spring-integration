package rs.mimitic.kevbow;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.messaging.MessageChannel;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@Configuration
public class SpringIntegrationCh0801DemoApplication implements ApplicationRunner {

    private static final String INPUT_DIR = "/home/mimitic/Documents/intellij_idea_ws/kevbow-mimitic-spring-integration/ch08_01_read_file/src/main/resources/";  // resource dir
    private static final String FILE_PATTERN = "sample.txt";

    public static void main(String[] args) {
        SpringApplication.run(SpringIntegrationCh0801DemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws IOException {

    }

    @Bean
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel printChannel() {
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
    @Transformer(inputChannel = "inboundChannel", outputChannel = "printChannel")
    public FileToStringTransformer fileToStringTransformer() {
        return new FileToStringTransformer();
    }
}
