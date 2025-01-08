package itmo.coursework.services;

import itmo.coursework.dto.GameHistoryMutationDTO;
import itmo.coursework.dto.GameHistoryResponseDTO;
import itmo.coursework.dto.ProfileResponseDTO;
import itmo.coursework.exceptions.entity.impl.GameHistoryExistenceException;
import itmo.coursework.exceptions.entity.impl.ProfileExistenceException;
import itmo.coursework.model.entity.GameHistory;
import itmo.coursework.model.entity.Profile;
import itmo.coursework.model.repository.GameHistoryRepository;
import itmo.coursework.model.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameHistoryService {

    private final GameHistoryRepository gameHistoryRepository;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;

    public GameHistoryService(GameHistoryRepository gameHistoryRepository, ProfileService profileService, ProfileRepository profileRepository) {
        this.gameHistoryRepository = gameHistoryRepository;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
    }


    public Page<GameHistoryResponseDTO> getAllGameHistory(Pageable pageable) {
        return gameHistoryRepository.findAll(pageable).map(this::getDTOFromGameHistory);
    }


    public GameHistoryResponseDTO getGameHistoryById(Long id) {
        return gameHistoryRepository.findById(id).map(this::getDTOFromGameHistory)
                .orElseThrow(() -> new GameHistoryExistenceException(
                        "GameHistory с id="
                                + id
                                + "не существует"
                ));
    }


    @Transactional
    public GameHistoryResponseDTO createGameHistory(GameHistoryMutationDTO gameHistoryMutationDTO) {
        GameHistory gameHistory = getGameHistoryFromDTO(gameHistoryMutationDTO);
        gameHistory = gameHistoryRepository.save(gameHistory);

        return getDTOFromGameHistory(gameHistory);
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public GameHistoryResponseDTO updateGameHistory(Long id, GameHistoryMutationDTO gameHistoryMutationDTO) {
        GameHistory gameHistory = gameHistoryRepository.findById(id)
                .orElseThrow(() -> new GameHistoryExistenceException(
                        "GameHistory с id="
                                + id
                                + "не существует"
                ));
        Profile profile = profileRepository.findById(gameHistoryMutationDTO.profileId())
                .orElseThrow(() -> new GameHistoryExistenceException(
                        "Profile с id="
                                + gameHistoryMutationDTO.profileId()
                                + "не существует"
                ));
        gameHistory.setProfile(profile);
        gameHistory.setEventName(gameHistoryMutationDTO.eventName());
        gameHistory.setGameResult(gameHistoryMutationDTO.gameResult());
        gameHistory.setDateEvent(gameHistoryMutationDTO.dateEvent());

        GameHistory updatedGameHistory = gameHistoryRepository.save(gameHistory);
        return getDTOFromGameHistory(updatedGameHistory);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGameHistory(Long id) {
        gameHistoryRepository.deleteById(id);
    }


    private GameHistoryResponseDTO getDTOFromGameHistory(GameHistory gameHistory) {
        if (gameHistory.getProfile() != null){
            ProfileResponseDTO profileResponseDTO = profileService.getDTOFromProfile(gameHistory.getProfile());
            return new GameHistoryResponseDTO(
                    gameHistory.getId(),
                    gameHistory.getEventName(),
                    gameHistory.getDateEvent(),
                    gameHistory.getGameResult(),
                    profileResponseDTO
            );
        }
        throw new ProfileExistenceException("Profile не существует");
    }

    private GameHistory getGameHistoryFromDTO(GameHistoryMutationDTO gameHistoryMutationDTO) {
        GameHistory gameHistory = new GameHistory();
        Profile profile = profileRepository.findById(gameHistoryMutationDTO.profileId())
                        .orElseThrow(() -> new GameHistoryExistenceException(
                                "Profile с id="
                                + gameHistoryMutationDTO.profileId()
                                + " не существует"
                        ));
        gameHistory.setProfile(profile);
        gameHistory.setEventName(gameHistoryMutationDTO.eventName());
        gameHistory.setDateEvent(gameHistoryMutationDTO.dateEvent());
        gameHistory.setGameResult(gameHistoryMutationDTO.gameResult());

        return gameHistory;
    }
}
