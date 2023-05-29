package com.railway.finalProject.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Connection settings of Capcha
 *
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
@Configuration
public class CaptchaSettings {

    private String site;
    private String secret;

}
