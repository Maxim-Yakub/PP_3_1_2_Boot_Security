package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "users")
public class
User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
}
