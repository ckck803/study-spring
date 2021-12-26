package com.example.samplecascade.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
//        @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
        @OneToMany(mappedBy = "person", cascade = CascadeType.MERGE)
    //    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    //    @OneToMany(mappedBy = "person")
    //    @OneToMany(mappedBy = "person", cascade = CascadeType.REFRESH)
//    @OneToMany(mappedBy = "person", cascade = CascadeType.DETACH)
    private List<Phone> phones = new ArrayList<>();

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setPerson(this);
    }
}
