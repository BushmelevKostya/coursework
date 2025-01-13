package itmo.coursework.api.controller;

import itmo.coursework.dto.ProfileMutationDTO;
import itmo.coursework.dto.ProfileResponseDTO;
import itmo.coursework.services.ProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class UserController {

    private final ProfileService profileService;

    public UserController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @GetMapping
    public Page<ProfileResponseDTO> findAll(Pageable pageable) {
        return profileService.getAllProfiles(pageable);
    }


    @GetMapping("/id/{id}")
    public ProfileResponseDTO findProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }
    
    @GetMapping("/username")
    public ProfileResponseDTO findProfileByUsername() {
        return profileService.getProfileByUsername();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponseDTO createProfile(@RequestBody ProfileMutationDTO profileMutationDTO) {
        return profileService.createProfile(profileMutationDTO);
    }


    @PutMapping("/{id}")
    public ProfileResponseDTO updateProfile(@PathVariable Long id, @RequestBody ProfileMutationDTO profileMutationDTO){
        return profileService.updateProfile(id, profileMutationDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.deleteProfile(id);
        return ResponseEntity.ok().build();
    }
}
