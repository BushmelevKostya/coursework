package itmo.coursework.api.controller;

import itmo.coursework.dto.GameHistoryMutationDTO;
import itmo.coursework.dto.GameHistoryResponseDTO;
import itmo.coursework.services.GameHistoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/gamehistory")
public class GameHistoryController {

    private final GameHistoryService gameHistoryService;

    public GameHistoryController(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }


    @GetMapping
    public Page<GameHistoryResponseDTO> findAll(Pageable pageable) {
        return gameHistoryService.getAllGameHistory(pageable);
    }


    @GetMapping("/{id}")
    public GameHistoryResponseDTO findGameHistoryById(@PathVariable Long id) {
        return gameHistoryService.getGameHistoryById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameHistoryResponseDTO createGameHistory(@RequestBody GameHistoryMutationDTO gameHistoryMutationDTO) {
        return gameHistoryService.createGameHistory(gameHistoryMutationDTO);
    }


    @PutMapping("/{id}")
    public GameHistoryResponseDTO updateGameHistory(@PathVariable Long id, @RequestBody GameHistoryMutationDTO gameHistoryMutationDTO) {
        return gameHistoryService.updateGameHistory(id, gameHistoryMutationDTO);
    }
}
