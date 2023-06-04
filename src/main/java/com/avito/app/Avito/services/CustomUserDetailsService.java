package com.avito.app.Avito.services;

import com.avito.app.Avito.models.Account;
import com.avito.app.Avito.models.enums.Role;
import com.avito.app.Avito.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {  //метод для того чтобы
//        return userRepository.findByEmail(email);                 // определять и подгружать user по email
//    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {   //метод по которому будет логин
        Account myUser = userRepository.findByEmail(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+userName);
        }
        if(!myUser.isActive()){
            throw new UsernameNotFoundException("Baned user: "+userName);

        }
        String[] role_arr = new String[myUser.getRoles().size()];
        int i = 0;
        for(Role str: myUser.getRoles()){
            role_arr[i] = String.valueOf(str).substring(5);
            i++;
        }

        Set<Role> s = myUser.getRoles();
        myUser.getRoles().size();
        UserDetails user = User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
                .roles(role_arr)
                .build();
        return user;
    }




}
