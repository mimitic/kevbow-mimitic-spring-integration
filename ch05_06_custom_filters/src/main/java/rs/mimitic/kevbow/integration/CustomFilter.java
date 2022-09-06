package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.core.MessageSelector;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Created by mimitic at 2022/Jan/21
 */
@Component
public class CustomFilter implements MessageSelector {

    @Override
    @Filter(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public boolean accept(final Message<?> message) {
        return message.getPayload().equals(7);
    }
}
