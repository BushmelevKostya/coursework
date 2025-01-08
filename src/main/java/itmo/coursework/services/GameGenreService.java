package itmo.coursework.services;

import itmo.coursework.dto.GameGenreMutationDTO;
import itmo.coursework.dto.GameGenreResponseDTO;
import itmo.coursework.dto.GameResponseDTO;
import itmo.coursework.dto.GenreResponseDTO;
import itmo.coursework.exceptions.entity.impl.GameExistenceException;
import itmo.coursework.exceptions.entity.impl.GameGenreExistenceException;
import itmo.coursework.exceptions.entity.impl.GenreExistenceException;
import itmo.coursework.model.entity.Game;
import itmo.coursework.model.entity.GameGenre;
import itmo.coursework.model.entity.Genre;
import itmo.coursework.model.repository.GameGenreRepository;
import itmo.coursework.model.repository.GameRepository;
import itmo.coursework.model.repository.GenreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameGenreService {

    private final GameGenreRepository gameGenreRepository;
    private final GameService gameService;
    private final GenreService genreService;
    private final GenreRepository genreRepository;
    private final GameRepository gameRepository;

    public GameGenreService(
            GameGenreRepository gameGenreRepository,
            GameService gameService,
            GenreService genreService,
            GenreRepository genreRepository,
            GameRepository gameRepository) {
        this.gameGenreRepository = gameGenreRepository;
        this.gameService = gameService;
        this.genreService = genreService;
        this.genreRepository = genreRepository;
        this.gameRepository = gameRepository;
    }


    public Page<GameGenreResponseDTO> getAllGameGenres(Pageable pageable) {
        return gameGenreRepository.findAll(pageable).map(this::getDTOFromGameGenre);
    }


    public GameGenreResponseDTO getGameGenreById(Long id) {
        return gameGenreRepository.findById(id).map(this::getDTOFromGameGenre)
                .orElseThrow(() -> new GameGenreExistenceException("GameGenre с id=" + id + " не существует"));
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public GameGenreResponseDTO createGameGenre(GameGenreMutationDTO gameGenreMutationDTO) {
        GameGenre gameGenre = getGameGenreFromDTO(gameGenreMutationDTO);
        gameGenre = gameGenreRepository.save(gameGenre);

        return getDTOFromGameGenre(gameGenre);
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public GameGenreResponseDTO updateGameGenre(Long id, GameGenreMutationDTO gameGenreMutationDTO) {
        GameGenre gameGenre = gameGenreRepository.findById(id)
                .orElseThrow(() -> new GameGenreExistenceException("Game genre с id=" + id + " не существует"));
        Game game = gameRepository.findById(gameGenreMutationDTO.gameId())
                .orElseThrow(() -> new GameExistenceException("Game с id=" + gameGenreMutationDTO.gameId() + " не существует"));
        gameGenre.setGame(game);
        Genre genre = genreRepository.findById(gameGenreMutationDTO.genreId())
                .orElseThrow(() -> new GenreExistenceException("Genre с id=" + gameGenreMutationDTO.genreId() + " не существует"));
        gameGenre.setGenre(genre);
        gameGenre = gameGenreRepository.save(gameGenre);

        return getDTOFromGameGenre(gameGenre);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGameGenre(Long id) {
        gameGenreRepository.deleteById(id);
    }


    private GameGenreResponseDTO getDTOFromGameGenre(GameGenre gameGenre) {
        if (gameGenre.getGame() == null) {
            throw new GameExistenceException("Game не указана");
        }

        if (gameGenre.getGenre() == null) {
            throw new GenreExistenceException("Genre не указан");
        }

        GameResponseDTO gameResponseDTO = gameService.getDTOFromGame(gameGenre.getGame());
        GenreResponseDTO genreResponseDTO = genreService.getDTOFromGenre(gameGenre.getGenre());

        return new GameGenreResponseDTO(
                gameGenre.getId(),
                gameResponseDTO,
                genreResponseDTO
        );
    }

    private GameGenre getGameGenreFromDTO(GameGenreMutationDTO gameGenreMutationDTO) {
        GameGenre gameGenre = new GameGenre();
        Game game = gameRepository.findById(gameGenreMutationDTO.gameId())
                .orElseThrow(() -> new GameExistenceException("Game с id=" + gameGenreMutationDTO.gameId() + " не существует"));
        Genre genre = genreRepository.findById(gameGenreMutationDTO.genreId())
                .orElseThrow(() -> new GenreExistenceException("Genre с id=" + gameGenreMutationDTO.genreId() + " не существует"));
        gameGenre.setGame(game);
        gameGenre.setGenre(genre);

        return gameGenre;
    }
}
