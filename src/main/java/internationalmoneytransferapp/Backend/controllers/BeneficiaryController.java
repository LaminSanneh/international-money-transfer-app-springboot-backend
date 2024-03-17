package internationalmoneytransferapp.Backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internationalmoneytransferapp.Backend.entities.Beneficiary;
import internationalmoneytransferapp.Backend.repositories.BeneficiaryRepository;

@RestController
@RequestMapping("/api/beneficiary")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @PostMapping("/{userId}")
    public Beneficiary addBeneficiary(@PathVariable Integer userId, @RequestBody Beneficiary beneficiary) {
        beneficiary.setUserId(userId);
        return beneficiaryRepository.save(beneficiary);
    }

    @GetMapping("/{userId}")
    public Iterable<Beneficiary> getBeneficiariesByUserId(@PathVariable Integer userId) {
        return beneficiaryRepository.findByUserId(userId);
    }
}
