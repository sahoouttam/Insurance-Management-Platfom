package com.project.imp.controller;

import com.project.imp.entity.InsurancePolicy;
import com.project.imp.service.InsurancePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InsurancePolicyController {

    @Autowired
    private final InsurancePolicyService insurancePolicyService;

    public InsurancePolicyController(InsurancePolicyService insurancePolicyService) {
        this.insurancePolicyService = insurancePolicyService;
    }

    @GetMapping("/policies")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<List<InsurancePolicy>> getAllInsurancePolicies() {
        List<InsurancePolicy> insurancePolicies = insurancePolicyService.findAllInsurancePolicies();
        return new ResponseEntity<>(insurancePolicies, HttpStatus.OK);
    }

    @GetMapping("/policies/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<InsurancePolicy> getInsurancePolicyById(@PathVariable long id) {
        InsurancePolicy insurancePolicy = insurancePolicyService.findInsurancePolicyById(id);
        return new ResponseEntity<>(insurancePolicy, HttpStatus.OK);
    }

    @PostMapping("/policies")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<InsurancePolicy> createInsurancePolicy(@Valid @RequestBody InsurancePolicy insurancePolicy) {
        InsurancePolicy newInsurancePolicy = insurancePolicyService.createInsurancePolicy(insurancePolicy);
        return new ResponseEntity<>(newInsurancePolicy, HttpStatus.CREATED);
    }

    @PutMapping("/policies/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<InsurancePolicy> updateInsurancePolicy(@PathVariable long id, @Valid @RequestBody InsurancePolicy insurancePolicy) {
        InsurancePolicy newInsurancePolicy = insurancePolicyService.updateInsurancePolicy(id, insurancePolicy);
        return new ResponseEntity<>(newInsurancePolicy, HttpStatus.OK);
    }

    @DeleteMapping("/policies/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteInsurancePolicy(@PathVariable long id) {
        insurancePolicyService.deleteInsurancePolicy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
