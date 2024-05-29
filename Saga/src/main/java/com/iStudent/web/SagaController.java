package com.iStudent.web;

import com.iStudent.model.DTOs.StudentDTO;
import com.iStudent.model.DTOs.ParentDTO;
import com.iStudent.model.DTOs.SubjectDTO;
import com.iStudent.service.SagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/saga")
public class SagaController {

    @Autowired
    private final SagaService sagaService;


    @Autowired
    public SagaController(SagaService sagaService) {
        this.sagaService = sagaService;
    }

    public static class StudentAndParentDTO {
        private StudentDTO studentDTO;
        private ParentDTO parentDTO;
        private List<SubjectDTO> subjectDTOs;
    
        public StudentDTO getStudentDTO() {
            return studentDTO;
        }
    
        public void setStudentDTO(StudentDTO studentDTO) {
            this.studentDTO = studentDTO;
        }
    
        public ParentDTO getParentDTO() {
            return parentDTO;
        }
    
        public void setParentDTO(ParentDTO parentDTO) {
            this.parentDTO = parentDTO;
        }

        public List<SubjectDTO> getSubjectDTOs() {
            return subjectDTOs;
        }
    
        public void setSubjectDTOs(List<SubjectDTO> subjectDTOs) {
            this.subjectDTOs = subjectDTOs;
        }
    }
    
    

    @PostMapping
    public String addStudent(@Valid @RequestBody StudentAndParentDTO dto, UriComponentsBuilder uriComponentsBuilder) { 
        sagaService.addStudent(dto.getStudentDTO(), dto.getParentDTO(), dto.getSubjectDTOs());

        return "Sucesso";
    }
}
