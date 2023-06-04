package com.avito.app.Avito.controller;

import com.avito.app.Avito.models.Account;
import com.avito.app.Avito.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")        //вернем страницу регистрации
    public String registration(){
        return "registration";
    }

    @GetMapping("/login")        //вернем страницу входа
    public String login(){

        return "login";
    }

    @PostMapping("/registration")        //зарегестрируем пользователя
    public String createUser(Account account, Model model){
        System.out.println(account);
        if(!userService.createUser(account)){          //если такой юзер уже есть то
            model.addAttribute("errorMessage", "Пользователь уже зареган с email " + account.getEmail());
            return "registration";
        }
        userService.createUser(account);
        return "redirect:/login";
    }


    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") Account user, Model model){    //можно сразу передать user это нам спринг помогает
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProducts());
        return "user-info";
    }

    @GetMapping("/hello")
    public String securityUrl(){
        return "hello";
    }
}
