package com.group1.ecocredit.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@Entity
@Table(name="confirmation_email")
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationEmail {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;
    @OneToOne
    @Fetch(FetchMode.JOIN)
    Pickup pickup;

    boolean emailSent;

    int retryCounter = 0;
}
