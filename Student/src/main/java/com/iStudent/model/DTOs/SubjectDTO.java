package com.iStudent.model.DTOs;

import javax.validation.constraints.NotBlank;

public class SubjectDTO {

    private Long id;

    @NotBlank
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
