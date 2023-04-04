package com.project.imp.service;

import com.project.imp.entity.Claim;
import com.project.imp.exception.ResourceNotException;
import com.project.imp.repository.ClaimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimService {

    @Autowired
    private final ClaimRepository claimRepository;

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public List<Claim> findAllClaims() {
        return claimRepository.findAll();
    }

    public Claim findClaimById(long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotException("Not found claim with id: " + id));
    }

    public Claim createClaim(Claim claim) {
        return claimRepository.save(claim);
    }

    public Claim updateClaim(long id, Claim claim) {
        Claim newClaim = claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotException("Not found claim with id: " + id));
        newClaim.setClaimNumber(claim.getClaimNumber());
        newClaim.setDescription(claim.getDescription());
        newClaim.setClaimDate(claim.getClaimDate());
        newClaim.setClaimStatus(claim.isClaimStatus());
        return newClaim;
    }

    public void deleteClaim(long id) {
        claimRepository.deleteById(id);
    }
}
