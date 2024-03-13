package com.group1.ecocredit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Bid")
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

    private Long Id;
    private Long waste_id;
    private Integer base_price;
    private Integer top_bid_amount;
    private Integer user_id;
    private boolean is_active;
    private LocalDateTime date;
    private boolean sold;
}
