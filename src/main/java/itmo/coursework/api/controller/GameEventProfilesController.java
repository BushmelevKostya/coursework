package itmo.coursework.api.controller;

import itmo.coursework.dto.GameEventProfilesMutationDTO;
import itmo.coursework.dto.GameEventProfilesResponseDTO;
import itmo.coursework.dto.GameEventResponseDTO;
import itmo.coursework.model.entity.GameEventProfiles;
import itmo.coursework.services.GameEventProfilesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gameeventprofiles")
public class GameEventProfilesController {

    private final GameEventProfilesService gameEventProfilesService;

    public GameEventProfilesController(GameEventProfilesService gameEventProfilesService) {
        this.gameEventProfilesService = gameEventProfilesService;
    }


    @GetMapping
    public Page<GameEventProfilesResponseDTO> getAllGameEventProfiles(Pageable pageable) {
        return gameEventProfilesService.getAllGameEventProfiles(pageable);
    }

    @GetMapping("/{id}")
    public GameEventProfilesResponseDTO getGameEventProfiles(@PathVariable Long id) {
        return gameEventProfilesService.getGameEventProfiles(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameEventProfilesResponseDTO createGameEventProfiles(@RequestBody GameEventProfilesMutationDTO gameEventProfilesMutationDTO) {
        return gameEventProfilesService.createGameEventProfile(gameEventProfilesMutationDTO);
    }


    @PutMapping("/{id}")
    public GameEventProfilesResponseDTO updateGameEventProfiles(@PathVariable Long id, @RequestBody GameEventProfilesMutationDTO gameEventProfilesMutationDTO) {
        return gameEventProfilesService.updateGameEventProfile(id, gameEventProfilesMutationDTO);
    }
}
