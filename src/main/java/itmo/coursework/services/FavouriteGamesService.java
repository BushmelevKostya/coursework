package itmo.coursework.services;

import itmo.coursework.dto.FavouriteGamesMutationDTO;
import itmo.coursework.dto.FavouriteGamesResponseDTO;
import itmo.coursework.dto.GameResponseDTO;
import itmo.coursework.dto.ProfileResponseDTO;
import itmo.coursework.exceptions.entity.impl.FavouriteGamesException;
import itmo.coursework.exceptions.entity.impl.GameExistenceException;
import itmo.coursework.exceptions.entity.impl.GameGenreExistenceException;
import itmo.coursework.exceptions.entity.impl.ProfileExistenceException;
import itmo.coursework.model.entity.FavouriteGames;
import itmo.coursework.model.entity.Game;
import itmo.coursework.model.entity.Profile;
import itmo.coursework.model.repository.FavouriteGamesRepository;
import itmo.coursework.model.repository.GameRepository;
import itmo.coursework.model.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavouriteGamesService {

    private final FavouriteGamesRepository favouriteGamesRepository;
    private final ProfileService profileService;
    private final GameService gameService;
    private final ProfileRepository profileRepository;
    private final GameRepository gameRepository;

    public FavouriteGamesService(
            FavouriteGamesRepository favouriteGamesRepository,
            ProfileService profileService,
            GameService gameService,
            ProfileRepository profileRepository,
            GameRepository gameRepository) {
        this.favouriteGamesRepository = favouriteGamesRepository;
        this.profileService = profileService;
        this.gameService = gameService;
        this.profileRepository = profileRepository;
        this.gameRepository = gameRepository;
    }

    public Page<FavouriteGamesResponseDTO> getAllFavouriteGames(Pageable pageable) {
        return favouriteGamesRepository.findAll(pageable).map(this::getDTOFromFavouriteGames);
    }


    public FavouriteGamesResponseDTO getFavouriteGamesById(Long id) {
        return favouriteGamesRepository.findById(id).map(this::getDTOFromFavouriteGames)
                .orElseThrow(() -> new FavouriteGamesException("FavouriteGames с id=" + id + " не существует"));
    }


    //TODO admin method
    @Transactional
    public FavouriteGamesResponseDTO createFavouriteGames(FavouriteGamesMutationDTO favouriteGamesMutationDTO) {
        FavouriteGames favouriteGames = getFavouriteGamesFromDTO(favouriteGamesMutationDTO);
        favouriteGames = favouriteGamesRepository.save(favouriteGames);

        return getDTOFromFavouriteGames(favouriteGames);
    }


    //TODO admin method
    @Transactional
    public FavouriteGamesResponseDTO updateFavouriteGames(Long id, FavouriteGamesMutationDTO favouriteGamesMutationDTO) {
        FavouriteGames favouriteGames = favouriteGamesRepository.findById(id)
                .orElseThrow(() -> new FavouriteGamesException("FavouriteGames с id=" + id + " не существует"));
        Profile profile = profileRepository.findById(favouriteGamesMutationDTO.profileId())
                .orElseThrow(() -> new ProfileExistenceException("Profile с id=" + favouriteGamesMutationDTO.profileId() + " не существует"));
        favouriteGames.setProfile(profile);
        Game game = gameRepository.findById(favouriteGamesMutationDTO.gameId())
                .orElseThrow(() -> new GameExistenceException("Game с id=" + favouriteGamesMutationDTO.gameId() + " не существует"));
        favouriteGames.setGame(game);
        favouriteGames = favouriteGamesRepository.save(favouriteGames);

        return getDTOFromFavouriteGames(favouriteGames);
    }


    private FavouriteGamesResponseDTO getDTOFromFavouriteGames(FavouriteGames favouriteGames) {
        if (favouriteGames.getGame() == null) {
            throw new GameExistenceException("Game не указана");
        }

        if (favouriteGames.getProfile() == null) {
            throw new ProfileExistenceException("Profile не указан");
        }

        ProfileResponseDTO profileResponseDTO = profileService.getDTOFromProfile(favouriteGames.getProfile());
        GameResponseDTO gameResponseDTO = gameService.getDTOFromGame(favouriteGames.getGame());

        return new FavouriteGamesResponseDTO(
                favouriteGames.getId(),
                profileResponseDTO,
                gameResponseDTO
        );
    }

    private FavouriteGames getFavouriteGamesFromDTO(FavouriteGamesMutationDTO favouriteGamesMutationDTO) {
        FavouriteGames favouriteGames = new FavouriteGames();
        Profile profile = profileRepository.findById(favouriteGamesMutationDTO.profileId())
                .orElseThrow(() -> new ProfileExistenceException("Game с id=" + favouriteGamesMutationDTO.gameId() + " не существует"));
        Game game = gameRepository.findById(favouriteGamesMutationDTO.gameId())
                .orElseThrow(() -> new GameExistenceException("Game с id=" + favouriteGamesMutationDTO.gameId() + " не существует"));
        favouriteGames.setProfile(profile);
        favouriteGames.setGame(game);

        return favouriteGames;
    }
}
