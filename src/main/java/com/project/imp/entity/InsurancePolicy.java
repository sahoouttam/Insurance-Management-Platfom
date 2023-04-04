package com.project.imp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "insurance_policies")
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "type")
    private String type;

    @Column(name = "coverage_amount")
    private int coverageAmount;

    @Column(name = "premium")
    private int premium;

    @Column(name = "start_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @Column(name = "end_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", referencedColumnName = "id")
    private List<Claim> claims;
}
