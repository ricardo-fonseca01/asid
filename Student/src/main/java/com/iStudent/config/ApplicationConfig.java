package com.iStudent.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    @Value("${club.base.url}")
    private String clubbaseurl;

    @Value("${parent.base.url}")
    private String parentbaseurl;

    @Value("${subject.base.url}")
    private String subjectbaseurl;

    @Bean
    public ModelMapper createModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public WebClient clubWebClient() {
        return WebClient.builder().baseUrl(clubbaseurl).build();
    }

    @Bean
    public WebClient parentWebClient() {
        return WebClient.builder().baseUrl(parentbaseurl).build();
    }

    @Bean
    public WebClient subjectWebClient() {
        return WebClient.builder().baseUrl(subjectbaseurl).build();
    }
}
