package com.group1.ecocredit.models;

import com.group1.ecocredit.dto.Waste;
import com.group1.ecocredit.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="pickup")
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

//    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

//    @OneToMany(mappedBy = "pickup")
//    private List<Waste> wastes;

//    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;


}
