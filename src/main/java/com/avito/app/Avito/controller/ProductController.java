package com.avito.app.Avito.controller;


import com.avito.app.Avito.models.Product;
import com.avito.app.Avito.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String products(@RequestParam(name="title", required = false) String title,
                           Principal principal, Model model)//берем параметр title из запроса но он не обязателен
    {
        model.addAttribute("products", productService.allProducts(title));//передадим в передаваемую модель все товары
        model.addAttribute("user", productService.getUserBtPrincipal(principal));
        return "products";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,     //это наши изображения,
                                @RequestParam("file2") MultipartFile file2,     //которые мы устанавливаем в формочке
                                @RequestParam("file3") MultipartFile file3,
                                Product product, Principal principal) throws IOException        //нам в ответ приходят данные а спринг сам подставляет их в наш объект
    {
        productService.saveProduct(principal,product, file1,file2,file3);            //добавляем наш товар в бд principal это текущий пользователь
        return "redirect:/";                            //и редиектим на главную
    }
    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);           //удаление товара
        return "redirect:/";

    }

    @GetMapping("product/{id}")             //детальный осмотр товара
    public String productInfo(@PathVariable Long id, Model model){
        Product product = productService.getProductById(id);        //брем наш товар по id

        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());      //устанавливаем наши изобржания чтобы вывести их
        return "product-info";
    }


}
