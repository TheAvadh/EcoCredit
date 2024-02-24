package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

@Data
@Entity
@Table(name = "waste")
public class Waste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "category_id")
    private Integer category_id;

    @Column(name = "pickup_id")
    private int pickup_id;

    private float weight;

}
