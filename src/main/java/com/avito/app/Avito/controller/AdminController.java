package com.avito.app.Avito.controller;

import com.avito.app.Avito.models.Account;
import com.avito.app.Avito.models.enums.Role;
import com.avito.app.Avito.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

//@PreAuthorize("hasRole('ADMIN')")         //если пользователь админ то дать допуск к контроллеру
@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")               //страничка админа
    public String admin(Model model){
        model.addAttribute("users", userService.list_users());
        return "admin";
    }

    @PostMapping("/admin/user/ban/{id}")                //бан эзера
    public String userBan(@PathVariable("id") Long id){
        userService.banUser(id);
        return "redirect:/admin";
    }
    @GetMapping("/admin/user/edit/{user}")              //страница для редактирования чужого профиля
    public String userEdit(@PathVariable("user")Account user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }
    @PostMapping("/admin/user/edit")            //редактирование пользователя
    public String userEdit(@RequestParam("userId") Account user, @RequestParam Map<String, String> form){
        userService.changeUserRoles(user, form);
        return "redirect:/admin";
    }

    @PostMapping("/admin/user/delete/{id}")
    public String userDelete(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
