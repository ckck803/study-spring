package com.example.samplequery.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {

    private String title;
    private String subTitle;
    private String content;
}
