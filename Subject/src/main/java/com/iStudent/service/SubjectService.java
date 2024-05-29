package com.iStudent.service;

import org.modelmapper.ModelMapper;
import org.hibernate.annotations.Parent;
import com.iStudent.model.entity.Subject;
import com.iStudent.model.DTOs.SubjectDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.iStudent.repository.SubjectRepository;

@Service
public class SubjectService {

    @Autowired
    public final SubjectRepository subjectRepository;

    @Autowired
    private final ModelMapper mapper;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, ModelMapper mapper) {
        this.subjectRepository = subjectRepository;
        this.mapper = mapper;
    }

    public List<SubjectDTO> getAllSubjects() {
        return subjectRepository.
                findAll().
                stream().
                map(this::mapToSubjectDTO).
                toList();
    }

    public Optional<SubjectDTO> getSubjectById(Long subjectId) {
        return subjectRepository
                .findById(subjectId)
                .map(this::mapToSubjectDTO);
    }

    public Subject getSubjectDetails(Long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        Subject subject = optionalSubject.get();
        return subject;
    }

    private SubjectDTO mapToSubjectDTO(Subject subject) {
        return mapper.map(subject, SubjectDTO.class);
    }

    public long addSubject(SubjectDTO subjectDTO) {

        Subject subject = mapper.map(subjectDTO, Subject.class);

        subjectRepository.save(subject);

        return subject.getId();
    }

    public List<Long> addSubjects(List<SubjectDTO> subjectDTOs) {
        return subjectDTOs.stream()
                .map(this::addSubject)
                .collect(Collectors.toList());
    }

}
