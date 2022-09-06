package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/15
 */
@Service
public class PrintReverseService {

    @ServiceActivator(inputChannel = "inputChannel")
    public void printReverse(final String messageToReverse) {
        System.out.println(new StringBuilder(messageToReverse).reverse().toString());
    }
}
