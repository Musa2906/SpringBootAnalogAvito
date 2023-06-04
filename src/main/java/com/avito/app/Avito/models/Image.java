package com.avito.app.Avito.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="originalFileName")
    private String originalFileName;        //имя самого файла
    @Column(name="size")
    private Long size;                      //размер файла
    @Column(name="contentType")
    private String contentType;             //рассширение файла
    @Column(name="isPreviewImage")
    private boolean isPreviewImage;         //та фотография которая будет отображаться в списке товаров. из трех фото
    @Lob                                    //для хранения байт символов
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] bytes;                   //поток байт, а именно само изображение
    //ALTER TABLE images DROP COLUMN bytes;
    //ALTER TABLE images ADD COLUMN bytes bytea;    - поменм тип в таблице. это именно в postgres, чтобы передавать большие файлы

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Product product;

}
