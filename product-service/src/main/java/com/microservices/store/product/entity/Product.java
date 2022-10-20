package com.microservices.store.product.entity;


import com.microservices.store.product.utils.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    private String name;
    @Column(length = 200)
    private String description;

    private Double stock;
    private Double price;

    @Column(name="status", nullable = false, length = 30 )
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}