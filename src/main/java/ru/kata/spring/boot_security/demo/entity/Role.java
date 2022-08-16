package ru.kata.spring.boot_security.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data //будут сгенерированы гетеры и сетеры ломбоком
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
