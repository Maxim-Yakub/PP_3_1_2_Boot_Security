package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final
    UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save (User user) {
        repository.save(user);
    }

    @Transactional
    public List<User> getAll () {
        return (List<User>) repository.findAll();
    }

    @Transactional
    public User get(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void update(Long id, User updatedUser){
        User userToBeUpdated = get(id);
        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setLastName(updatedUser.getLastName());
        userToBeUpdated.setEmail(updatedUser.getEmail());
    }

    @Transactional
    public List<User> search(String keyword) {
        return repository.search(keyword);
    }
}
