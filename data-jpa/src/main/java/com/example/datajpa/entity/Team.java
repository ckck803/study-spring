package com.example.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;

    // 한 팀에 여러명의 회원이 가입하는 개념
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
}
