package com.iStudent.service;

import com.iStudent.model.DTOs.ParentDTO;
import com.iStudent.model.DTOs.StudentDTO;
import com.iStudent.model.DTOs.SubjectDTO;

import reactor.core.publisher.Mono;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class SagaService {

    @Autowired
    @Qualifier("studentWebClient")
    private WebClient studentWebClient;

    @Autowired
    @Qualifier("parentWebClient")
    private WebClient parentWebClient;

    @Autowired
    @Qualifier("subjectWebClient")
    private WebClient subjectWebClient;

    public String addStudent(StudentDTO studentDTO, ParentDTO parentDTO, List<SubjectDTO> subjectDTOs) {
        Long studentId = studentWebClient.post().uri("/student/").bodyValue(studentDTO).retrieve().bodyToMono(Long.class).block();
        Long parentId = parentWebClient.post().uri("/parent/").bodyValue(parentDTO).retrieve().bodyToMono(Long.class).block();
        List<Long> subjectIds = subjectWebClient.post().uri("/add/").bodyValue(subjectDTOs).retrieve().bodyToMono(new ParameterizedTypeReference<List<Long>>() {}).block();

        studentWebClient.post().uri("/"+ parentId + "/parent/" + studentId + "/").retrieve().toEntity(StudentDTO.class).block();
        //studentWebClient.post().uri("/student/" + studentId + "/").bodyValue(subjectIds).retrieve().bodyToMono(Void.class).block();

        return "Sucesso";
    }

}
