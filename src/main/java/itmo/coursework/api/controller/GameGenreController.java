package itmo.coursework.api.controller;

import itmo.coursework.dto.GameGenreMutationDTO;
import itmo.coursework.dto.GameGenreResponseDTO;
import itmo.coursework.services.GameGenreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/gamegenre")
public class GameGenreController {

    private final GameGenreService gameGenreService;

    public GameGenreController(GameGenreService gameGenreService) {
        this.gameGenreService = gameGenreService;
    }


    @GetMapping
    public Page<GameGenreResponseDTO> findAll(Pageable pageable) {
        return gameGenreService.getAllGameGenres(pageable);
    }


    @GetMapping("/{id}")
    public GameGenreResponseDTO findGameGenreById(@PathVariable Long id) {
        return gameGenreService.getGameGenreById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GameGenreResponseDTO createGameGenre(@RequestBody GameGenreMutationDTO gameGenreMutationDTO) {
        return gameGenreService.createGameGenre(gameGenreMutationDTO);
    }


    @PutMapping("/{id}")
    public GameGenreResponseDTO udateGameGenre(@PathVariable Long id, @RequestBody GameGenreMutationDTO gameGenreMutationDTO) {
        return gameGenreService.updateGameGenre(id, gameGenreMutationDTO);
    }
}
