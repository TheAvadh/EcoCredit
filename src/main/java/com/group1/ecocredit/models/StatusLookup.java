package com.group1.ecocredit.models;

import com.group1.ecocredit.enums.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "status_lookup")
@Getter
public class StatusLookup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    Status status;

}
