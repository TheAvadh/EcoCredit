package com.group1.ecocredit.models;

import jakarta.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "waste_id")
    private Waste waste;

    @Column(name="base_price")
    private Double base_price;

    @Column(name="top_bid_amount")
    private Double top_bid_amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name ="is_active")
    private boolean is_active;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="sold")
    private boolean sold;
}
