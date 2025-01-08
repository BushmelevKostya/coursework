package itmo.coursework.api.controller;

import itmo.coursework.dto.GameEventMutationDTO;
import itmo.coursework.dto.GameEventResponseDTO;
import itmo.coursework.services.GameEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
