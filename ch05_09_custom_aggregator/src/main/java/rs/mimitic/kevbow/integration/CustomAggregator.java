package rs.mimitic.kevbow.integration;

import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.aggregator.AbstractAggregatingMessageGroupProcessor;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.store.MessageGroup;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by mimitic at 2022/Jan/21
 */
@Component
public class CustomAggregator extends AbstractAggregatingMessageGroupProcessor {

    @Override
    @Aggregator(inputChannel = "aggregatorChannel", outputChannel = "outputChannel")  // doesn't seem to work this way...!
    protected Object aggregatePayloads(final MessageGroup group, final Map<String, Object> defaultHeaders) {
        final StringBuilder sb = new StringBuilder();
        group.getMessages().forEach(message -> {
            System.out.println(message.getHeaders().get(IntegrationMessageHeaderAccessor.CORRELATION_ID));
            sb.append(message.getPayload());
        });
        //sb.append("]");
        return sb.toString();
    }
}
