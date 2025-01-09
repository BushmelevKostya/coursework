package itmo.coursework.api.controller;

import itmo.coursework.dto.GameEventMutationDTO;
import itmo.coursework.dto.GameEventResponseDTO;
import itmo.coursework.services.GameEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/v1/gameevent")
public class GameEventController {

    private final GameEventService gameEventService;

    public GameEventController(GameEventService gameEventService) {
        this.gameEventService = gameEventService;
    }


    @GetMapping
    public Page<GameEventResponseDTO> findAll(Pageable pageable) {
        return gameEventService.getAllGameEvents(pageable);
    }


    @GetMapping("/{id}")
    public GameEventResponseDTO findGameEventById(@PathVariable Long id) {
        return gameEventService.getGameEventById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameEventResponseDTO createGameEvent(@RequestBody GameEventMutationDTO gameEventMutationDTO) {
        return gameEventService.createGameEvent(gameEventMutationDTO);
    }


    @PutMapping("/{id}")
    public GameEventResponseDTO updateGameEvent(@PathVariable Long id,
                                                @RequestBody GameEventMutationDTO gameEventMutationDTO) {
        return gameEventService.updateGameEvent(id, gameEventMutationDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameEvent(@PathVariable Long id) {
        gameEventService.deleteGameEvent(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/available")
    public Page<GameEventResponseDTO> getAvailableGameEvents(Pageable pageable) {
        return gameEventService.getAvailableGameEvents(pageable);
    }

    @GetMapping("/filter")
    public Page<GameEventResponseDTO> filterGameEvents(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "game.name", required = false) String gameName,
            @RequestParam(value = "location.address", required = false) String locationName,
            @RequestParam(value = "status.status", required = false) String statusName,
            @RequestParam(value = "minMembers", required = false) Integer minMembers,
            @RequestParam(value = "maxMembers", required = false) Integer maxMembers,
            Pageable pageable) {

        return gameEventService.filterGameEvents(
                name,
                description,
                gameName,
                locationName,
                statusName,
                minMembers,
                maxMembers,
                pageable);
    }
}
