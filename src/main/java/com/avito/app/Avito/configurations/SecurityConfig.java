package com.avito.app.Avito.configurations;

import com.avito.app.Avito.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity      //указываем что это конфиг для securityC
@EnableGlobalMethodSecurity(prePostEnabled = true)      //нужно для добавления аннотаций по ограничениям методов
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/", "/product/**", "/images/**", "/registration", "/user/**")   //укажем какие urls требуют авторизации
                .permitAll()
                .anyRequest().authenticated()       //я хз вроде для всех разрешено
                .and()      //и (то есть это след блок с другими разрешениями уже)
                .formLogin()
                .loginPage("/login")
                .permitAll()        //для не зарегестрированных пользователей так же можно
                .and()
                .logout()               //выйти из аккаунта
                .permitAll();        //для не зарегестрированных пользователей так же можнo
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)   //указываем как именно нужно подгружать юзеров
                .passwordEncoder(passwordEncoder());         //указываем как он будет рассшифровывыать эти пароли
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);    //шифрование пароля с силой 8
    }
}
