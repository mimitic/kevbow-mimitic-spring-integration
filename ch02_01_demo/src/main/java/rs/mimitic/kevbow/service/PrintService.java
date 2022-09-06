package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/15
 */
@Service
public class PrintService {

    //@ServiceActivator(inputChannel = "inputChannel")
    public void print(final String message) {
        System.out.println(message);
    }
}
