package com.example.springboottemplate.user.common.recaptcha;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class RecaptchaService {
    private final RecaptchaConfig recaptchaConfig;
    private final RestTemplate restTemplate;

    public RecaptchaResponse validateToken(String recaptchaToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", recaptchaConfig.secretKey());
        map.add("response", recaptchaToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        ResponseEntity<RecaptchaResponse> response =
                restTemplate.exchange(recaptchaConfig.verifyUrl(), HttpMethod.POST, entity, RecaptchaResponse.class);

        return response.getBody();
    }
}
