package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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

/*
I
также можно пустить на какую-нибудь панельку
только по определенным ролям, f.e.:
.antMatchers("/admin/**").hasAnyRole("ADMIN", "SUPERADMIN")
если не админ и не суперадмин, то вылетит ошибка прав доступа
*/

/*
II
.hasAuthority и .hasAnyRole похожи

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
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}