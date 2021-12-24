package com.example.samplequery.domain;

import com.example.samplequery.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "TITLE", length = 50, nullable = false)
    public String title;

    @Column(name = "SUBTITLE", length = 100, nullable = true)
    public String subTitle;

    @Column(name = "CONTENT", nullable = true)
    @Lob
    public String content;
}
