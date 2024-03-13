package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Bid")
@NoArgsConstructor
@AllArgsConstructor
public class Bid {

    @Id
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waste_id")
    private Waste waste;

    @Column(name="base_price")
    private Integer base_price;

    @Column(name="top_bid_amount")
    private Integer top_bid_amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name ="is_active")
    private boolean is_active;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="sold")
    private boolean sold;
}
