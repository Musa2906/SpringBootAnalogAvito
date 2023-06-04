package com.avito.app.Avito.repositories;

import com.avito.app.Avito.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);     //поиск по email
}
