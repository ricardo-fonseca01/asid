package com.iStudent.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig {

    @Value("${student.base.url}")
    private String studentbaseurl;

    @Value("${parent.base.url}")
    private String parentbaseurl;

    @Value("${subject.base.url}")
    private String subjectbaseurl;

    @Bean
    public ModelMapper createModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public WebClient studentWebClient() {
        return WebClient.builder().baseUrl(studentbaseurl).build();
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
