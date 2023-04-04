package com.project.imp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "claims")
@AllArgsConstructor
@NoArgsConstructor
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "claim_number")
    private String claimNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "claim_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate claimDate;

    @Column(name = "claim_status")
    private boolean claimStatus;

}
