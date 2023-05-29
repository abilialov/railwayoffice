package com.railway.finalProject.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Capcha settings
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponceDto {

    private boolean success;
    @JsonAlias("error-codes")
    private Set<String> errorCodes;
}
