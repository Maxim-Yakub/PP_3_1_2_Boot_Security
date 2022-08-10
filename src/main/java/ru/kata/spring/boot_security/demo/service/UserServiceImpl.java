package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public void save (User user) {
        repository.save(user);
    }

    public List<User> getAll () {
        return (List<User>) repository.findAll();
    }

    public User get(Long id) {
        return repository.findById(id).get();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void update(User updatedUser){
        save(updatedUser);
    }
    public List<User> search(String keyword) {
        return repository.search(keyword);
    }

    @Override
    public User findUserByName(String userName) {
        return repository.findUserByName(userName);
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findUserByName(userName)

        return null;
    }
}
