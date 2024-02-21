package com.group1.ecocredit.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "status_lookup")
public class Status {
    @Id
    private Integer id;

    @Column(name = "value")
    private String value;

}
