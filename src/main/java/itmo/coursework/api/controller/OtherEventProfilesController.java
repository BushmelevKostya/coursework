package itmo.coursework.api.controller;

import itmo.coursework.dto.OtherEventProfilesMutationDTO;
import itmo.coursework.dto.OtherEventProfilesResponseDTO;
import itmo.coursework.model.entity.OtherEventProfiles;
import itmo.coursework.services.OtherEventProfilesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/othereventprofiles")
public class OtherEventProfilesController {

    private final OtherEventProfilesService otherEventProfilesService;

    public OtherEventProfilesController(OtherEventProfilesService otherEventProfilesService) {
        this.otherEventProfilesService = otherEventProfilesService;
    }


    @GetMapping
    public Page<OtherEventProfilesResponseDTO> getAllOtherEventProfiles(Pageable pageable) {
        return otherEventProfilesService.getAllOtherEventProfiles(pageable);
    }


    @GetMapping("/{id}")
    public OtherEventProfilesResponseDTO getOtherEventProfiles(@PathVariable Long id) {
        return otherEventProfilesService.getOtherEventProfiles(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OtherEventProfilesResponseDTO createOtherEventProfiles(@RequestBody OtherEventProfilesMutationDTO otherEventProfilesMutationDTO) {
        return otherEventProfilesService.createOtherEventProfile(otherEventProfilesMutationDTO);
    }


    @PutMapping("/{id}")
    public OtherEventProfilesResponseDTO updateOtherEventProfiles(@PathVariable Long id,
                                                                  @RequestBody OtherEventProfilesMutationDTO otherEventProfilesMutationDTO) {
        return otherEventProfilesService.updateOtherEventProfile(id, otherEventProfilesMutationDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOtherEventProfiles(@PathVariable Long id) {
        otherEventProfilesService.deleteOtherEventProfile(id);
        return ResponseEntity.ok().build();
    }
}
