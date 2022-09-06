package rs.mimitic.kevbow.integration;

import org.springframework.stereotype.Component;

/**
 * Created by mimitic at 2022/Jan/22
 */
@Component
public class CustomHeaderEnricher {

    public String getHeaderValue() {
        return "This is the header value.";
    }
}
