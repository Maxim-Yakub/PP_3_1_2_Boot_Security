package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void save(User user);
    List<User> getAll ();
    User get(Long id);
    void delete(Long id);
    void update(User updatedUser);
    List<User> search(String keyword);

    User findByUsername(String username);
}
