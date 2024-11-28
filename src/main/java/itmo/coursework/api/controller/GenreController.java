package itmo.coursework.api.controller;


import itmo.coursework.dto.GenreMutationDTO;
import itmo.coursework.dto.GenreResponseDTO;
import itmo.coursework.services.GenreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }


    @GetMapping
    public Page<GenreResponseDTO> findAll(Pageable pageable) {
        return genreService.getAllGenres(pageable);
    }


    @GetMapping("/{id}")
    public GenreResponseDTO findGenreById(@PathVariable Long id) {
        return genreService.getGenreById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreResponseDTO createGenre(@RequestBody GenreMutationDTO genreMutationDTO) {
        return genreService.createGenre(genreMutationDTO);
    }

    @PutMapping("/{id}")
    public GenreResponseDTO updateGenre(@PathVariable Long id, @RequestBody GenreMutationDTO genreMutationDTO) {
        return genreService.updateGenre(id, genreMutationDTO);
    }


}
