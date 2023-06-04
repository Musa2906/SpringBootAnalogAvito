package com.avito.app.Avito.services;


import com.avito.app.Avito.models.Account;
import com.avito.app.Avito.models.Image;
import com.avito.app.Avito.models.Product;
import com.avito.app.Avito.repositories.ProductRepository;
import com.avito.app.Avito.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service            // укажем что это сервис
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Product> allProducts(String title){ //функция возвращающая все продукты
        if(title != null) return productRepository.findByTitle(title);     //если передано название то выводит по названию
        return productRepository.findAll();             //иначе выводит все
    }

    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
    //принимает на вход модель товара, и объекты картинок. Principal - ЭТО ТЕКУЩИЙ ПОЛЬЗОВАТЕЛЬ. И НЕКОТОРЫЕ ДАННЫЕ О НЕМ
        product.setUser(getUserBtPrincipal(principal)); //кладем вовладельца товара, нашего текущего пользователя
        Image image1;
        Image image2;
        Image image3;
        if(file1.getSize() != 0){
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);           //установим её при пролистывании списка товаров она будет отображаться
            product.addImageToProduct(image1);      //добавим изображение в список продуктов. и наоборот
        }
        if(file2.getSize() != 0){
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);      //добавим изображение в список продуктов. и наоборот
        }
        if(file3.getSize() != 0){
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);      //добавим изображение в список продуктов. и наоборот
        }
        Product productFromDb = productRepository.save(product);        //сохраняем наш объект в бд
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());          //берем его, и делаем
        // лицивую картинку, беря первый элемент списка картинок, и устанавливаем его на объект
        productRepository.save(product);            //и снова сохраняем, но теперь с лицивой картинкой
    }

    public Account getUserBtPrincipal(Principal principal) {        //берет пользователя по email переданного в principal
        if(principal == null) return new Account(); // если такого юзера не существует, то вернем пустого. это нужно чтобы нам не выводился пользователь на странице. ибо передать null нельзя
        return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {      //метод, который преобразует MultipartFile в Image
        Image image = new Image();      //присваиваем все данные переданного файла изображения. нашему классу картинки
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id){     //вернем товар по id
        return productRepository.findById(id).orElse(null);         //ищем товар по id если его нет, то вернем null
    }

}
