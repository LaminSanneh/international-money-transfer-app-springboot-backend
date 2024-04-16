package internationalmoneytransferapp.Backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internationalmoneytransferapp.Backend.dto.SavedBeneficiaryDTO;
import internationalmoneytransferapp.Backend.entities.SavedBeneficiary;
import internationalmoneytransferapp.Backend.entities.UserEntity;
import internationalmoneytransferapp.Backend.entities.SavedBeneficiary;
import internationalmoneytransferapp.Backend.repositories.BeneficiaryRepository;
import internationalmoneytransferapp.Backend.repositories.UserRepository;
import internationalmoneytransferapp.Backend.services.exceptions.CannotCreateSavedBeneficiaryException;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Iterable<SavedBeneficiary>> getBeneficiaries() {
        // List<Beneficiary> beneficiaries = beneficiaryService.getAllByUserId(currentUser.getId());
        // Iterable<SavedBeneficiary> beneficiaries = beneficiaryRepository.findAll();
        Iterable<SavedBeneficiary> beneficiaries = beneficiaryRepository.findAllBySavedById(1);
        return ResponseEntity.ok(beneficiaries);
    }

    @PostMapping
    public ResponseEntity<SavedBeneficiary> addBeneficiary(@RequestBody SavedBeneficiaryDTO beneficiaryDTO) {

        Integer userId = beneficiaryDTO.getSaverId();

        if (userId == null) {
            throw new CannotCreateSavedBeneficiaryException("User id(saverId) for beneficiary owner not provided");
        }

        Optional<UserEntity> saverOptional = userRepository.findById(userId);

        if (saverOptional.isEmpty()) {
            throw new CannotCreateSavedBeneficiaryException("User with id does not exist");
        }

        UserEntity saver = saverOptional.get();

        SavedBeneficiary beneficiary = new SavedBeneficiary();
        beneficiary.setName(beneficiaryDTO.getName());
        beneficiary.setSavedBy(saver);

        SavedBeneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);

        return ResponseEntity.ok(savedBeneficiary);
    }

    @DeleteMapping("/{beneficiaryId}")
    public ResponseEntity<Boolean> deleteBeneficiaryById(@PathVariable Integer beneficiaryId) {

        beneficiaryRepository.deleteById(beneficiaryId);

        return ResponseEntity.ok(true);
    }
}
