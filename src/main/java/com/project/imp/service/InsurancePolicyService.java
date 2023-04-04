package com.project.imp.service;

import com.project.imp.entity.InsurancePolicy;
import com.project.imp.exception.ResourceNotException;
import com.project.imp.repository.InsurancePolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsurancePolicyService {

    @Autowired
    private final InsurancePolicyRepository insurancePolicyRepository;

    public InsurancePolicyService(InsurancePolicyRepository insurancePolicyRepository) {
        this.insurancePolicyRepository = insurancePolicyRepository;
    }

    public List<InsurancePolicy> findAllInsurancePolicies() {
        List<InsurancePolicy> insurancePolicies = insurancePolicyRepository.findAll();
        if (insurancePolicies.isEmpty()) throw new ResourceNotException("No data found");
        return insurancePolicies;
    }

    public InsurancePolicy findInsurancePolicyById(long id) {
        return insurancePolicyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotException("Not found policy with id: " + id));
    }

    public InsurancePolicy createInsurancePolicy(InsurancePolicy insurancePolicy) {
        return insurancePolicyRepository.save(insurancePolicy);
    }

    public InsurancePolicy updateInsurancePolicy(long id, InsurancePolicy insurancePolicy) {
        InsurancePolicy newInsurancePolicy = insurancePolicyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotException("Not found policy with id: " + id));;
        newInsurancePolicy.setPolicyNumber(insurancePolicy.getPolicyNumber());
        newInsurancePolicy.setType(insurancePolicy.getType());
        newInsurancePolicy.setCoverageAmount(insurancePolicy.getCoverageAmount());
        newInsurancePolicy.setPremium(insurancePolicy.getPremium());
        newInsurancePolicy.setStartDate(insurancePolicy.getStartDate());
        newInsurancePolicy.setEndDate(insurancePolicy.getEndDate());
        newInsurancePolicy.setClaims(insurancePolicy.getClaims());
        return newInsurancePolicy;
    }

    public void deleteInsurancePolicy(long id) {
        insurancePolicyRepository.deleteById(id);
    }
}
