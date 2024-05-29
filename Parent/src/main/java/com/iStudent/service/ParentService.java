package com.iStudent.service;

import com.iStudent.model.DTOs.ParentDTO;
import com.iStudent.model.entity.Parent;
import com.iStudent.repository.ParentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentService {

    private final ParentRepository parentRepository;

    private final ModelMapper mapper;

    @Autowired
    public ParentService(ParentRepository parentRepository, ModelMapper mapper) {
        this.parentRepository = parentRepository;
        this.mapper = mapper;
    }

    public Parent findParentById(Long parentId) {
        return parentRepository.findById(parentId).orElse(null);
    }

    public Parent getParentDetails(Long id) {
        Optional<Parent> optionalParent = parentRepository.findById(id);
        Parent parent = optionalParent.get();
        return parent;
    }

    public List<ParentDTO> getAllParents() {
        return parentRepository.
                findAll().
                stream().
                map(this::mapToParentDTO).
                toList();
    }

    private ParentDTO mapToParentDTO(Parent parent) {
        return mapper.map(parent, ParentDTO.class);
    }

    public Optional<ParentDTO> getParentById(Long parentId) {
        return parentRepository.
                findById(parentId).
                map(this::mapToParentDTO);
    }

    public long addParent(ParentDTO parentDTO) {

        Parent parent = mapper.map(parentDTO, Parent.class);

        parentRepository.save(parent);

        return parent.getId();

    }

    public long addParent2(ParentDTO parentDTO) {

        Parent parent = mapper.map(parentDTO, Parent.class);

        parentRepository.save(parent);

        return parent.getId();

    }

    public void deleteParentById(Long parentId) {

        parentRepository.deleteById(parentId);
    }

}
