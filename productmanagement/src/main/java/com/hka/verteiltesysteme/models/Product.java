package com.hka.verteiltesysteme.models;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
