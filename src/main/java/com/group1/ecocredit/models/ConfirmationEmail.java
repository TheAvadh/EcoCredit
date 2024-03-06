package com.group1.ecocredit.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Data
@Entity
@Table(name="pickup")
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationEmail {

    @OneToOne
    @Fetch(FetchMode.JOIN)
    Pickup pickup;

    boolean emailSent;


    int retryCounter = 5;
}
