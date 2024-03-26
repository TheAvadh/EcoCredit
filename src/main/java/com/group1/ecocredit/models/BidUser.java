package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Bid_all")
@NoArgsConstructor
@AllArgsConstructor
public class BidUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name = "waste_type")
    private String waste_type;

    @Column(name = "weight")
    private Float waste_weight;

    @Column(name = "bid_amount")
    private Double bid_amount;

    @Column(name = "is_Active")
    private Boolean is_Active;

    @Column(name = "highest_bid")
    private Double highest_bid;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bid_id")
    private Bid bid;
}
