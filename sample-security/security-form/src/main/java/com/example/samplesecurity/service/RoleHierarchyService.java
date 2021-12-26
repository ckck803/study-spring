package com.example.samplesecurity.service;

import com.example.samplesecurity.domain.RoleHierarchy;
import com.example.samplesecurity.repository.RoleHierarchyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleHierarchyService {
    @Autowired
    private RoleHierarchyRepository roleHierarchyRepository;

    @Transactional
    public String findAllHierarchy(){
        List<RoleHierarchy> roleHierarchies = roleHierarchyRepository.findAll();
        StringBuilder concatedRoles = new StringBuilder();

        roleHierarchies.stream().forEach( roleHierarchy -> {
            if(roleHierarchy.getParentName() != null){
                concatedRoles.append(roleHierarchy.getParentName().getChildName());
                concatedRoles.append(" > ");
                concatedRoles.append(roleHierarchy.getChildName());
                concatedRoles.append("\n");
            }
        });

        return concatedRoles.toString();
    }
}
