package com.example.springboottemplate.user.common.recaptcha;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecaptchaResponse(
        Boolean success,
        @JsonProperty("challenge_ts") String challengeTimeStamp,
        String hostname,
        Double score,
        String action) {}
