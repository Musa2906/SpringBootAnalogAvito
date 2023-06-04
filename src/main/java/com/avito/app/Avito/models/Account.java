package com.avito.app.Avito.models;

import com.avito.app.Avito.models.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="account")
@Data
public class Account implements UserDetails {          //имплиментируем для того чтобы в Spring Security, эта модель была Account со всеми нужными методами
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email", unique = true)        //униикальный email
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="name")
    private String name;

    @Column(name="active")
    private boolean active;             //для того, чтобы замораживать аккаунты. или если они пока не поддтверждены

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="image_id")    //укажем не avatar_id а image_id
    private Image avatar;

    @Column(name="password", length = 1000)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)  //это не набор сущностей, а набор простых типов (строк и т.д.)
    @CollectionTable(name="user_role",                                  //это многие ко многим. но при этом у нас
            joinColumns = @JoinColumn(name = "user_id"))             //сопостовляются Account с String ролями это из JavaEE
    @Enumerated(EnumType.STRING)    //преобразовываем enum в string
    private Set<Role> roles = new HashSet<>();          //это у нас словарь. где будут все роли для одного пользователя


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<Product> products = new ArrayList<>();

    private LocalDateTime dateOfCreates;

    @PrePersist
    private void init(){
        dateOfCreates = LocalDateTime.now();
    }

    public boolean isAdmin(){return roles.contains(Role.ROLE_ADMIN);}   //метод проверяющий админ ли это

    //методы из UserDetails для Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
