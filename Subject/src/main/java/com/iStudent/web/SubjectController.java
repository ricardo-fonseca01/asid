package com.iStudent.web;

import com.iStudent.model.DTOs.SubjectDTO;
import com.iStudent.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getAllSubjects() {
        return ResponseEntity
                .ok(subjectService.getAllSubjects());
    }

    @GetMapping("/subject/{id}")
    private ResponseEntity<SubjectDTO> getSubjectDetails(@PathVariable("id") Long id) {
        return this.subjectService.getSubjectById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable("id") Long subjectId) {
        Optional<SubjectDTO> subject = this.subjectService.getSubjectById(subjectId);

        if (subject.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(subject.get());

        }
    }

    @PostMapping("/subject/")
    public ResponseEntity<SubjectDTO> addSubject(@Valid @RequestBody SubjectDTO subjectDTO,
                                               UriComponentsBuilder uriComponentsBuilder) {

        long newSubjectId = subjectService.addSubject(subjectDTO);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/subjects/{id}")
                        .build(newSubjectId))
                .build();
    }

    @PostMapping("/add/")
    public ResponseEntity<List<Long>> addSubjects(@Valid @RequestBody List<SubjectDTO> subjectDTOs,
                                                  UriComponentsBuilder uriComponentsBuilder) {

        List<Long> subjectIds = subjectDTOs.stream()
                .map(subjectDTO -> subjectService.addSubject(subjectDTO))
                .collect(Collectors.toList());

        return ResponseEntity
                .created(uriComponentsBuilder.path("/subjects").build().toUri())
                .body(subjectIds);
    }

}
