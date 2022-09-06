package rs.mimitic.kevbow.integration;

import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Created by mimitic at 2022/Jan/22
 */
@Component
public class CustomTransformer {

    @Transformer(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public String transform(final Message<String> message) {
        final String[] tokens = message.getPayload().split(" ");
        final StringBuilder sb = new StringBuilder(tokens[tokens.length-1]);
        for (int i = tokens.length - 2; i > -1; i--) {
            sb.append(", ".concat(tokens[i]));
        }
        return sb.toString();
    }
}
