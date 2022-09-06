package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.Splitter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mimitic at 2022/Jan/21
 */
@Component
public class CustomSplitter {

    @Splitter(inputChannel = "inputChannel", outputChannel = "outputChannel")
    protected List<String> split(final Message<?> message) {
        return new ArrayList<>(Arrays.asList(message.getPayload().toString().split(" ")));
    }
}
