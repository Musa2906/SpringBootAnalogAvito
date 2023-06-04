package com.avito.app.Avito.repositories;

import com.avito.app.Avito.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> { //имеет встроеные crud операции
    List<Product> findByTitle(String title);        //этот интерфейс по названию метода уже понимает что делать
}
