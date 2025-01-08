package itmo.coursework.api.controller;

import itmo.coursework.dto.GameEventMutationDTO;
import itmo.coursework.dto.GameEventResponseDTO;
import itmo.coursework.model.repository.GameEventRepository;
import itmo.coursework.services.GameEventService;
import itmo.coursework.services.ShedulerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gameevent")
public class GameEventController {

    private final GameEventService gameEventService;
    private final ShedulerService shedulerService;
    private final GameEventRepository gameEventRepository;
    
    public GameEventController(GameEventService gameEventService, ShedulerService shedulerService, GameEventRepository gameEventRepository) {
        this.gameEventService = gameEventService;
        this.shedulerService = shedulerService;
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
}
