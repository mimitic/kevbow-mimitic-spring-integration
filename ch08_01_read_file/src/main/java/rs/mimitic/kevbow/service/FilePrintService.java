package rs.mimitic.kevbow.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/15
 */
@Service
public class FilePrintService {

    @ServiceActivator(inputChannel = "printChannel")
    public void filePrintingMessageHandler(final String fileMessage) {
        System.out.println("Printed message: " + fileMessage);
    }
}
