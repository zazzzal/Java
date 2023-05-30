package com.example.springboot.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PostAllAction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer first;
    private Integer second;
    private String answer;

    public PostAllAction(Integer first, Integer second, String answer) {
        this.first = first;
        this.second = second;
        this.answer = answer;
    }
}
