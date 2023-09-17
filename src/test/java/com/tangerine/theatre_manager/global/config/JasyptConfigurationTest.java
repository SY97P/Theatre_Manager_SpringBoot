package com.tangerine.theatre_manager.global.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptConfigurationTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JasyptConfig jasyptConfiguration;

    @Test
    void jasypt() {
        String url = jasyptConfiguration.getDatasourceUrl();
        String username = jasyptConfiguration.getDatasourceUsername();
        String password = jasyptConfiguration.getDatasourcePassword();

        String encryptedUrl = jasyptConfiguration.jasyptEncrypt(url);
        String encryptedUserName = jasyptConfiguration.jasyptEncrypt(username);
        String encryptedPassword = jasyptConfiguration.jasyptEncrypt(password);

        logger.info("\noriginUrl: {} \nencryptedUrl: {}", url, encryptedUrl);
        logger.info("\noriginUsername: {} \nencryptedUsername: {}", url, encryptedUserName);
        logger.info("\noriginPassword: {} \nencryptedPassword: {}", url, encryptedPassword);

        assertThat(url).isEqualTo(jasyptConfiguration.jasyptDecrypt(encryptedUrl));
        assertThat(username).isEqualTo(jasyptConfiguration.jasyptDecrypt(encryptedUserName));
        assertThat(password).isEqualTo(jasyptConfiguration.jasyptDecrypt(encryptedPassword));
    }
}