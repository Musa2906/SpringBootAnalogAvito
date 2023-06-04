package com.avito.app.Avito.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="products")
@Data               //импортировано из зависимости lombok и прописывает геттеры и сеттеры итд, за нас
@AllArgsConstructor  //делает так, чтобы конструктор был со всеми нашими полями             ЭТО ДЕЛАЕТ КЛАСС POJO
@NoArgsConstructor      //делапет конструктор без параметров
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="description", columnDefinition = "text")      //укажем что тип данной колонки будет текст. и тогда ограничений по колво символов не будет
    private String description;
    @Column(name="price")
    private int price;
    @Column(name="city")
    private String city;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    private Long previewImageId;
    private LocalDateTime dateOfCreated;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn()
    private Account user;


    @PrePersist         //аннотация,которая срабатывает при создании объекта - init бина
    private void init(){                    //иницилизируем дату создания при создании
        dateOfCreated = LocalDateTime.now();
    }

    public void addImageToProduct(Image image){     //указываем картинке, к какому товару она пренадлежит.
        image.setProduct(this);
        images.add(image);                      //так же добавим картинку в список картинок товара
    }
}
