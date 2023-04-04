package com.project.imp.controller;

import com.project.imp.entity.Claim;
import com.project.imp.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClaimController {

    @Autowired
    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping("/claims")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<List<Claim>> getAllClaims() {
        List<Claim> claims = claimService.findAllClaims();
        return new ResponseEntity<>(claims, HttpStatus.OK);
    }

    @GetMapping("/claims/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<Claim> getClaimById(@PathVariable long id) {
        Claim claim = claimService.findClaimById(id);
        return new ResponseEntity<>(claim, HttpStatus.OK);
    }

    @PostMapping("/claims")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<Claim> createClaim(@Valid @RequestBody Claim claim) {
        Claim newClaim = claimService.createClaim(claim);
        return new ResponseEntity<>(newClaim, HttpStatus.CREATED);
    }

    @PutMapping("/claims/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<Claim> updateClaim(@PathVariable long id, @Valid @RequestBody Claim claim) {
        Claim updatedClaim = claimService.updateClaim(id, claim);
        return new ResponseEntity<>(updatedClaim, HttpStatus.OK);
    }

    @DeleteMapping("/claims/{id}")
    //@PreAuthorize("hasRole('USER') or hasRole('USER') or hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteClaimById(@PathVariable long id) {
        claimService.deleteClaim(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
