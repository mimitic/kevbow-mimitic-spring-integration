package rs.mimitic.kevbow.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by mimitic at 2022/Jan/25
 */
@Service
public class CustomPersonService {

    public String generatePhoneNumber() {
        return RandomStringUtils.randomNumeric(3).concat("-").concat(RandomStringUtils.randomNumeric(3));
    }
}
