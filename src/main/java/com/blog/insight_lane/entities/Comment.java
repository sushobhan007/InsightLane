package com.blog.insight_lane.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(length = 300, nullable = false)
    private String content;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

}
