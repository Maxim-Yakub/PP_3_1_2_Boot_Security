package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // на / и /индекс допускаются все
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()

                // п.4 CRUD только для админа
                .antMatchers("/admin/**").hasAnyRole("ADMIN")

/*
I
также можно пустить на какую-нибудь панельку
только по определенным ролям, f.e.:
.antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
если не админ и не суперадмин, то вылетит ошибка прав доступа
*/

/*
II
// права доступа это всего лишь текст
.hasAuthority право доступа куда можно, сверяет досканально
.hasAnyRole роль, наделенная правами доступа, дописывает ROLE_ и сверяет
*/

                // а на другие запросы уже только аутентифицированные
                .anyRequest().authenticated()

                .and()

                //httpBasic() - куда перенаправлять неаутенифицированнных
                // или тоже самое formLogin() - наша форма, либо стандарт спринга для регания
                .formLogin().successHandler(successUserHandler)
                // после формЛогина можно заменить стандартный /login через
                // .loginProccessingurl("/mylogin")

                .permitAll()
                .and()

                // этот стандартный, ведет на страничку логина
                // стандартный вопрос спринга разлогиниться
                .logout()
                // а .logout().logoutSuccessurl("/") туда куда скажем

                .permitAll();
    }

    // аутентификация inMemory
//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }

    // аутентификация JDBC
//    @Bean
//    public JdbcUserDetailsManager userDetailsManager (DataSource dataSource) {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        UserDetails admin =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user")
//                        .roles("USER")
//                        .build();
//
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.createUser(user);
//        userDetailsManager.createUser(admin);
//
//        return userDetailsManager;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        authenticationProvider.setUserDetailsService();
        return authenticationProvider;
    }

}