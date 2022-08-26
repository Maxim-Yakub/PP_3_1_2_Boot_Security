package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    String[] strings = {"ROLE_ADMIN", "USER"};
    Collection<Role> roles;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        roles = roleRepository.findRolesByUser(user);
        user.setAuthorityList(foAdmin(userRoles(roles)));
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s не найден", username));
        }
//        setRole(user);
        return org.springframework.security.core.userdetails.User.withUserDetails(user).build();

    }

    String[] userRoles(Collection<Role> roles) {
        String[] userRoles = roles.stream().map((role) -> "ROLE_" + role.getName()).toArray(String[]::new);
        return userRoles;
    }

    Collection<GrantedAuthority> foAdmin(String[] roles) {
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
        return authorities;
    }


//    public void setRole (User user) {
//        user.setUserRoles(user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new));
//    }

//    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
//        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
//        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
//        return authorities;
//    }
//public List<Role> findByUser (String username){
////    return userRepository.findByUsername(username);
//}public List<Role> findByUser (String username){
////    return userRepository.findByUsername(username);

}
