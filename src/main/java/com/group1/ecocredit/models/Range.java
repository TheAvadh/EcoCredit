package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "max_min_range")
@NoArgsConstructor
@AllArgsConstructor
public class Range {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "min_value")
        private Integer minValue;

        @Column(name = "max_value")
        private Integer maxValue;

        @Column(name = "increment_value")
        private Integer incrementValue;
    }



