package com.example.samplesecurity.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLE_HIERARCHY")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString(exclude = {"parentName", "roleHierarchy"})
public class RoleHierarchy implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "child_name")
    public String childName;


    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_name", referencedColumnName = "child_name")
    private RoleHierarchy parentName;

    @OneToMany(mappedBy = "parentName", cascade = {CascadeType.ALL })
    private Set<RoleHierarchy> roleHierarchy = new HashSet<>();
}
