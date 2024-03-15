package internationalmoneytransferapp.Backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import internationalmoneytransferapp.Backend.entities.Profile;
import internationalmoneytransferapp.Backend.repositories.ProfileRepository;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

	@Autowired
    private ProfileRepository profileRepository;

    // Update profile details
    @PutMapping("/{userId}")
    public Profile updateProfile(@PathVariable Integer userId, @RequestBody Profile updatedProfile) {
        Profile existingProfile = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        existingProfile.setFullName(updatedProfile.getFullName());
        existingProfile.setEmail(updatedProfile.getEmail());

        return profileRepository.save(existingProfile);
    }

    @GetMapping("/{userId}")
    public Profile getProfile(@PathVariable Integer userId) {
        return profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
