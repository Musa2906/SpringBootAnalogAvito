package com.avito.app.Avito.services;

import com.avito.app.Avito.models.Account;
import com.avito.app.Avito.models.enums.Role;
import com.avito.app.Avito.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {        //экстендим для переопределения метода логина
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public boolean createUser(Account user){
        String email = user.getEmail();
        if(userRepository.findByEmail(email) != null) {return false;}   //если такой пользователь уже есть то

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));       //кладем пароль шифруя его по нашему бину что мы создали
        user.getRoles().add(Role.ROLE_USER);//добавляем базовую роль юзера

        userRepository.save(user);      //сохраняем пользоваетеля
        return true;
    }

    public List<Account> list_users(){      //метод получения всех пользователей
        return userRepository.findAll();
    }


    public void banUser(Long id) {      // метод для бана пользователя
        Account user = userRepository.findById(id).orElse(null);
        if(user != null){
            if(user.isActive()){user.setActive(false);}     //если активен то бан, если бан то активируем
            else {user.setActive(true);}
        }
        userRepository.save(user);  //сохраняем новое состояние пользователя
    }

    public void changeUserRoles(Account user, Map<String, String> form) {       //смена роли пользователя через админку
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet()); //делаем строковый Set всех ролей для всех Role вызываем name то есть преобразовываем в строковый вид
        user.getRoles().clear(); //далее очищаем все текущие роли данного пользователя

        for(String key: form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));         //добавляем все роли которые мы передали в Map
            }
        }

        userRepository.save(user);          //и сохраняем
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
