package itmo.coursework.api.controller;

import itmo.coursework.dto.GameMutationDTO;
import itmo.coursework.dto.GameResponseDTO;
import itmo.coursework.services.GameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }


    @GetMapping
    public Page<GameResponseDTO> findAll(Pageable pageable) {
        return gameService.getAllGames(pageable);
    }


    @GetMapping("/{id}")
    public GameResponseDTO findGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameResponseDTO createGame(@RequestBody GameMutationDTO gameMutationDTO) {
        return gameService.createGame(gameMutationDTO);
    }


    @PutMapping("/{id}")
    public GameResponseDTO updateGame(@PathVariable Long id, @RequestBody GameMutationDTO gameMutationDTO) {
        return gameService.updateGame(id, gameMutationDTO);
    }
}
