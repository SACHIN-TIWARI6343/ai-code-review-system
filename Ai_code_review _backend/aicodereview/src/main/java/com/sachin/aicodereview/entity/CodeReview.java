package com.sachin.aicodereview.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "code_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodeReview {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String language;

    @Lob
    private String code;

    @Lob
    private String review;

    private LocalDateTime createdAt;
}
