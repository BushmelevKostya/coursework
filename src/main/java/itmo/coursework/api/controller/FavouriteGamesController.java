package itmo.coursework.api.controller;

import itmo.coursework.dto.FavouriteGamesMutationDTO;
import itmo.coursework.dto.FavouriteGamesResponseDTO;
import itmo.coursework.model.entity.FavouriteGames;
import itmo.coursework.services.FavouriteGamesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favouritegames")
public class FavouriteGamesController {

    private final FavouriteGamesService favouriteGamesService;

    public FavouriteGamesController(FavouriteGamesService favouriteGamesService) {
        this.favouriteGamesService = favouriteGamesService;
    }


    @GetMapping
    public Page<FavouriteGamesResponseDTO> findAll(Pageable pageable) {
        return favouriteGamesService.getAllFavouriteGames(pageable);
    }


    @GetMapping("/{id}")
    public FavouriteGamesResponseDTO findFavouriteGamesById(@PathVariable Long id) {
        return favouriteGamesService.getFavouriteGamesById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavouriteGamesResponseDTO createFavouriteGames(@RequestBody FavouriteGamesMutationDTO favouriteGamesMutationDTO) {
        return favouriteGamesService.createFavouriteGames(favouriteGamesMutationDTO);
    }


    @PutMapping("/{id}")
    public FavouriteGamesResponseDTO updateFavouriteGames(@PathVariable Long id, FavouriteGamesMutationDTO favouriteGamesMutationDTO) {
        return favouriteGamesService.updateFavouriteGames(id, favouriteGamesMutationDTO);
    }
}
