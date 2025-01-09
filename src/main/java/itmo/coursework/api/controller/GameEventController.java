package itmo.coursework.api.controller;

import itmo.coursework.dto.GameEventMutationDTO;
import itmo.coursework.dto.GameEventResponseDTO;
import itmo.coursework.model.repository.GameEventRepository;
import itmo.coursework.services.GameEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/gameevent")
public class GameEventController {

    private final GameEventService gameEventService;
    private final GameEventRepository gameEventRepository;

    public GameEventController(GameEventService gameEventService, GameEventRepository gameEventRepository) {
        this.gameEventService = gameEventService;
        this.gameEventRepository = gameEventRepository;
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
    public String deleteGameEvent(@PathVariable Long id,
                                  @RequestBody GameEventMutationDTO gameEventMutationDTO) {
        return gameEventService.deleteGameEvent(id, gameEventMutationDTO);
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

    @GetMapping("/by-district")
    public List<GameEventResponseDTO> findByDistrict(@RequestParam String districtName) {
        return gameEventRepository.findByDistrict(districtName).stream()
                .map(gameEventService::getDTOFromGameEvent)
                .toList();
    }

    @GetMapping("/by-city")
    public List<GameEventResponseDTO> findByCity(@RequestParam String cityName) {
        return gameEventRepository.findByCity(cityName).stream()
                .map(gameEventService::getDTOFromGameEvent)
                .toList();
    }

    @GetMapping("/by-game")
    public List<GameEventResponseDTO> findByGame(@RequestParam String gameName) {
        return gameEventRepository.findByGame(gameName).stream()
                .map(gameEventService::getDTOFromGameEvent)
                .toList();
    }

    @GetMapping("/with-min-members")
    public List<GameEventResponseDTO> findByMinimumMembers(@RequestParam int minMembers) {
        return gameEventRepository.findByMinimumMembers(minMembers).stream()
                .map(gameEventService::getDTOFromGameEvent)
                .toList();
    }

    @GetMapping("/by-genre")
    public List<GameEventResponseDTO> findByGameGenre(@RequestParam String genre) {
        return gameEventRepository.findByGameGenre(genre).stream()
                .map(gameEventService::getDTOFromGameEvent)
                .toList();
    }

    @GetMapping("/scheduled")
    public List<GameEventResponseDTO> findScheduledEvents() {
        return gameEventRepository.findScheduledEvents().stream()
                .map(gameEventService::getDTOFromGameEvent)
                .toList();
    }

    @GetMapping("/recommended/{id}")
    public List<GameEventResponseDTO> findRecommendedEvents(@PathVariable Long id) {
        return gameEventService.findRecommendedEvents(id);
    }
}
