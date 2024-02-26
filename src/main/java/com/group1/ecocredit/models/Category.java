package com.group1.ecocredit.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "category_lookup")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private Integer id;

    @Column(name = "value")
    private String value;
}
