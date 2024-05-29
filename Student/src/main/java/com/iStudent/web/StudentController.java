package com.iStudent.web;

import com.iStudent.model.DTOs.StudentDTO;
import com.iStudent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private final StudentService studentService;


    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity
                .ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable("id") Long studentId) {
        Optional<StudentDTO> student = this.studentService.getStudentById(studentId);

        if (student.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();

        } else {
            return ResponseEntity
                    .ok(student.get());

        }
    }

    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(@Valid @RequestBody StudentDTO studentDTO,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        long newStudentId = studentService.addStudent(studentDTO);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/students/{id}")
                        .build(newStudentId))
                .build();
    }

    @PostMapping("/student/")
    public Long addStudent2(@Valid @RequestBody StudentDTO studentDTO,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        long newStudentId = studentService.addStudent(studentDTO);

        ResponseEntity
            .created(uriComponentsBuilder.path("/students/{id}")
                .build(newStudentId))
            .build();
        return newStudentId;
    }

    @PostMapping("/{parentId}")
    public ResponseEntity<StudentDTO> addStudentParent(@Valid @RequestBody StudentDTO studentDTO,
                                                 UriComponentsBuilder uriComponentsBuilder, @PathVariable("parentId") Long parentId) {

        long newStudentId = studentService.addStudentParent(studentDTO, parentId);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/students/{id}")
                        .build(newStudentId))
                .build();
    }

    @PostMapping("/{clubId}/club/{studentId}")
    public ResponseEntity<StudentDTO> addClubToStudent(@PathVariable("clubId") Long clubId, @PathVariable("studentId") Long studentId) {
        Optional<StudentDTO> optionalDesiredStudent = studentService.addClubtoStudent(clubId, studentId);

        if (optionalDesiredStudent.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok(optionalDesiredStudent.get());
        }
    }

    @PostMapping("/{parentId}/parent/{studentId}")
    public ResponseEntity<StudentDTO> assignParentToStudent(@PathVariable("parentId") Long parentId, @PathVariable("studentId") Long studentId) {
        Optional<StudentDTO> studentToMapOptional = studentService.assignParentToStudent(parentId, studentId);

        if (studentToMapOptional.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok(studentToMapOptional.get());
        }

    }

    @PostMapping("/student/{studentId}")
    public ResponseEntity<StudentDTO> assignSubjectToStudent(@Valid @RequestBody List<Long> subjectIds, @PathVariable("studentId") Long studentId) {
        Optional<StudentDTO> studentToMapOptional = studentService.assignSubjectToStudent(subjectIds, studentId);

        if (studentToMapOptional.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        } else {
            return ResponseEntity
                    .ok(studentToMapOptional.get());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> deleteStudentById(@PathVariable("id") Long studentId) {
        this.studentService.deleteStudentById(studentId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
