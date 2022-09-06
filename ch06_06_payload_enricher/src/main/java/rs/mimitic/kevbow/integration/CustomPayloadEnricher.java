package rs.mimitic.kevbow.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import rs.mimitic.kevbow.model.Person;
import rs.mimitic.kevbow.service.CustomPersonService;

/**
 * Created by mimitic at 2022/Jan/25
 */
@Component
public class CustomPayloadEnricher {

    @Autowired
    private CustomPersonService personService;

    @Transformer(inputChannel = "inputChannel", outputChannel = "payloadEnricherChannel")
    public Message<Person> customEnricher(final Message<Person> message) {
        final Person person = message.getPayload();
        person.setPhoneNumber(this.personService.generatePhoneNumber());
        MessageHeaderAccessor headers = new MessageHeaderAccessor(message);
        return MessageBuilder.withPayload(person).setHeaders(headers).build();
    }
}
