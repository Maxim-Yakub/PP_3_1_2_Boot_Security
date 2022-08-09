package ru.kata.spring.boot_security.demo.service;

import com.example.pp_3_3_1_springboot_crud.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    List<User> getAll ();
    User get(Long id);
    void delete(Long id);
    void update(Long id, User updatedUser);
    List<User> search(String keyword);
}
