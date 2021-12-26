package com.example.samplecascade.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Phone {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID")
    public Person person;
}
