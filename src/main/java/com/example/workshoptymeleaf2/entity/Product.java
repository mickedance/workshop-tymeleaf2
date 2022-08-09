package com.example.workshoptymeleaf2.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Product {
    @Id
    @Column(updatable = false, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDate createdDate = LocalDate.now();
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    private Category category;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, Category category) {
        this(name);
        this.category = category;
    }

}
