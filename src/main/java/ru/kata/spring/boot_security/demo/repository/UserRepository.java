package ru.kata.spring.boot_security.demo.repository;

import com.example.pp_3_3_1_springboot_crud.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query(value="SELECT user from User user where user.name LIKE  CONCAT('%',:keyword,'%')"
            + "OR user.email LIKE CONCAT('%',:keyword,'%')"
            + "OR user.lastName LIKE CONCAT('%',:keyword,'%')")
    List<User> search(@Param("keyword") String keyword);

}
