package com.group1.ecocredit.models;

import com.group1.ecocredit.dto.WasteDto;
import com.group1.ecocredit.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="pickup")
public class Pickup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private int user_id;

    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @Setter
    private Status status;

}
