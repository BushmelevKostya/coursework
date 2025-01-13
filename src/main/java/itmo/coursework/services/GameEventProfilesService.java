package itmo.coursework.services;

import itmo.coursework.dto.*;
import itmo.coursework.exceptions.entity.impl.GameEventExistenceException;
import itmo.coursework.exceptions.entity.impl.GameEventProfilesException;
import itmo.coursework.exceptions.entity.impl.ProfileExistenceException;
import itmo.coursework.model.entity.Game;
import itmo.coursework.model.entity.GameEvent;
import itmo.coursework.model.entity.GameEventProfiles;
import itmo.coursework.model.entity.Profile;
import itmo.coursework.model.repository.GameEventProfilesRepository;
import itmo.coursework.model.repository.GameEventRepository;
import itmo.coursework.model.repository.GameRepository;
import itmo.coursework.model.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameEventProfilesService {

    private final GameEventProfilesRepository gameEventProfilesRepository;
    private final GameEventService gameEventService;
    private final ProfileService profileService;
    private final GameEventRepository gameEventRepository;
    private final ProfileRepository profileRepository;
    private final SecurityService securityService;

    public GameEventProfilesService(GameEventProfilesRepository gameEventProfilesRepository, GameEventService gameEventService, ProfileService profileService, GameEventRepository gameEventRepository, ProfileRepository profileRepository, SecurityService securityService) {
        this.gameEventProfilesRepository = gameEventProfilesRepository;
        this.gameEventService = gameEventService;
        this.profileService = profileService;
        this.gameEventRepository = gameEventRepository;
        this.profileRepository = profileRepository;
        this.securityService = securityService;
    }


    public Page<GameEventProfilesResponseDTO> getAllGameEventProfiles(Pageable pageable) {
        return gameEventProfilesRepository.findAll(pageable).map(this::getDTOFromGameEventProfiles);
    }


    public GameEventProfilesResponseDTO getGameEventProfiles(Long id) {
        return gameEventProfilesRepository.findById(id).map(this::getDTOFromGameEventProfiles)
                .orElseThrow(() -> new GameEventProfilesException("GameEventProfiles с id=" + id + " не существует"));
    }

//TODO: автовстраиваемый profile id
@Transactional
public GameEventProfilesResponseDTO createGameEventProfile(GameEventProfilesMutationDTO gameEventProfilesMutationDTO) {
    Long profileId = profileRepository.findByName(securityService.findUserName()).orElseThrow(
            () -> new ProfileExistenceException("Такого профиля не существует")
    ).getId();
    
    boolean exists = gameEventProfilesRepository.existsByProfileIdAndGameEventId(profileId, gameEventProfilesMutationDTO.eventId());
    if (exists) {
        throw new GameEventProfilesException("Данный профиль уже существует для этого события");
    }
    
    GameEvent gameEvent = gameEventRepository.findById(gameEventProfilesMutationDTO.eventId())
        .orElseThrow(() -> new GameEventExistenceException("GameEvent не существует"));
    if (gameEventProfilesRepository.countByGameEvent(gameEvent) + 2 > gameEvent.getMaxMembers()) {
        throw new GameEventProfilesException("Нет мест");
    }
    GameEventProfiles gameEventProfiles = gameEventProfilesRepository.insertGameEventProfile(profileId, gameEventProfilesMutationDTO.eventId());
    
//    gameEvent = gameEventRepository.findById(gameEventProfiles.getGameEvent().getId())
//            .orElseThrow(() -> new GameEventExistenceException("GameEvent не существует"));
    gameEvent.setCurrentMembers(gameEventProfilesRepository.countByGameEvent(gameEvent) + 1);
    gameEventRepository.save(gameEvent);
    
    return getDTOFromGameEventProfiles(gameEventProfiles);
}



    @Transactional
    public GameEventProfilesResponseDTO updateGameEventProfile(Long id, GameEventProfilesMutationDTO gameEventProfilesMutationDTO) {
        GameEventProfiles gameEventProfiles = gameEventProfilesRepository.findById(id)
                .orElseThrow(() -> new GameEventProfilesException("GameEventProfiles с id=" + id + " не существует"));
        GameEvent oldEvent = gameEventProfiles.getGameEvent();
        oldEvent.setCurrentMembers(oldEvent.getCurrentMembers()-1);
        gameEventRepository.save(oldEvent);
        GameEvent gameEvent = gameEventRepository.findById(gameEventProfilesMutationDTO.eventId())
                .orElseThrow(() -> new GameEventExistenceException("GameEvent с id=" + gameEventProfilesMutationDTO.eventId() + " не существует"));
//        Profile profile = profileRepository.findById(gameEventProfilesMutationDTO.profileId())
//                .orElseThrow(() -> new ProfileExistenceException("Profile с id=" + gameEventProfilesMutationDTO.profileId() + " не сущетсвует"));
//        gameEventProfiles.setProfile(profile);
        gameEventProfiles.setGameEvent(gameEvent);
        gameEventProfiles = gameEventProfilesRepository.save(gameEventProfiles);
        gameEvent.setCurrentMembers(gameEventProfilesRepository.countByGameEvent(gameEvent));
        gameEventRepository.save(gameEvent);

        return getDTOFromGameEventProfiles(gameEventProfiles);
    }


    private GameEventProfilesResponseDTO getDTOFromGameEventProfiles(GameEventProfiles gameEventProfiles) {
        if (gameEventProfiles.getGameEvent() == null) {
            throw new GameEventExistenceException("Game event не существует");
        }
        if (gameEventProfiles.getProfile() == null) {
            throw new ProfileExistenceException("Profile не существует");
        }

        GameEventResponseDTO gameEventResponseDTO = gameEventService.getDTOFromGameEvent(gameEventProfiles.getGameEvent());
        ProfileResponseDTO profileResponseDTO = profileService.getDTOFromProfile(gameEventProfiles.getProfile());

        return new GameEventProfilesResponseDTO(
                gameEventProfiles.getId(),
                profileResponseDTO,
                gameEventResponseDTO
        );
    }

    private GameEventProfiles getGameEventProfilesFromDTO(GameEventProfilesMutationDTO gameEventProfilesMutationDTO) {
        GameEventProfiles gameEventProfiles = new GameEventProfiles();
        GameEvent gameEvent = gameEventRepository.findById(gameEventProfilesMutationDTO.eventId())
                .orElseThrow(() -> new GameEventExistenceException("GameEvent c id=" + gameEventProfilesMutationDTO.eventId() + " не существует"));
        Profile profile = profileRepository.findByName(securityService.findUserName())
                .orElseThrow(() -> new ProfileExistenceException("Profile не существует"));
        gameEventProfiles.setGameEvent(gameEvent);
        gameEventProfiles.setProfile(profile);

        return gameEventProfiles;
    }

}
