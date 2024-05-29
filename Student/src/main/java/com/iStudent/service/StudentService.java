package com.iStudent.service;

import com.iStudent.model.DTOs.StudentDTO;
import com.iStudent.model.entity.Student;
import com.iStudent.model.entity.Club;
import com.iStudent.model.entity.Parent;
import com.iStudent.model.entity.Subject;
import com.iStudent.repository.StudentRepository;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    @Autowired
    private final ModelMapper mapper;

    @Autowired
    @Qualifier("clubWebClient")
    private WebClient clubWebClient;

    @Autowired
    @Qualifier("parentWebClient")
    private WebClient parentWebClient;

    @Autowired
    @Qualifier("subjectWebClient")
    private WebClient subjectWebClient;

    @Autowired
    public StudentService(StudentRepository studentRepository, ModelMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository
                .findAll()
                .stream()
                .map(this::mapToStudentDTO)
                .toList();
    }

    public Optional<StudentDTO> getStudentById(Long studentId) {
        return studentRepository
                .findById(studentId)
                .map(this::mapToStudentDTO);
    }

    @Transactional
    public Optional<StudentDTO> addClubtoStudent(Long clubId, Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = optionalStudent.get();
 
        // Using WebClient
        Club club = clubWebClient.get().uri("/club/" + clubId + "/").retrieve().bodyToMono(Club.class).block();
        student.setClub(club);
        studentRepository.save(student);

        return studentRepository.findById(studentId).map(s -> mapper.map(s, StudentDTO.class));
    }

    public Optional<StudentDTO> assignParentToStudent(Long parentId, Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Student student = studentOptional.get();

        // Using WebClient
        Parent parent = parentWebClient.get().uri("/parent/" + parentId + "/").retrieve().bodyToMono(Parent.class).block();

        student.setParent(parent);
        studentRepository.save(student);

        return studentRepository.findById(studentId).map(s -> mapper.map(s, StudentDTO.class));
    }

    public Optional<StudentDTO> assignSubjectToStudent(List<Long> subjectIds, Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Student student = studentOptional.get();

        for (Long subjectId : subjectIds) {
            Subject subject = subjectWebClient
                .get()
                .uri("/subject/" + subjectId + "/")
                .retrieve()
                .bodyToMono(Subject.class)
                .block();
            student.addSubject(subject);
        }

        studentRepository.save(student);
        return studentRepository.findById(studentId).map(s -> mapper.map(s, StudentDTO.class));
    }

    public long addStudent(StudentDTO studentDTO) {
        Student student = mapper.map(studentDTO, Student.class);

        studentRepository.save(student);

        return student.getId();
    }

    public long addStudent2(StudentDTO studentDTO) {
        Student student = mapper.map(studentDTO, Student.class);

        studentRepository.save(student);

        return student.getId();
    }

    public long addStudentParent(StudentDTO studentDTO, Long parentId) {
        Student student = mapper.map(studentDTO, Student.class);

        // Using WebClient
        Parent parent = parentWebClient.get().uri("/parent/" + parentId + "/").retrieve().bodyToMono(Parent.class).block();

        studentRepository.save(student);
        student.setParent(parent);
        studentRepository.save(student);

        return student.getId();
    }

    public void deleteStudentById(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    private StudentDTO mapToStudentDTO(Student student) {
        Hibernate.initialize(student.getSubjects());
        return mapper.map(student, StudentDTO.class);
    }

}
