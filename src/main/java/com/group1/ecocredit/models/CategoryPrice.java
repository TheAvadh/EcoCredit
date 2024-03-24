package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "category_price")
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "value")
    private Float value;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_id")
    private Category category;

}
