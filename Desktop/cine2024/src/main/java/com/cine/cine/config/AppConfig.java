package com.cine.cine.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhZjY3NzZiNjcyM2JhNjE1Mzg4OWE2NzE4NWQ0NmZiMyIsInN1YiI6IjY2NDgxZDRhMTg0YTQ2MzE3MjkyMzE1ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.B4ZSIedsj8gBMgWna8CM7eXK7FQZ7XpwH9hwYTED6_Y");
            return execution.execute(request, body);
        }));
        return restTemplate;
    }
}
