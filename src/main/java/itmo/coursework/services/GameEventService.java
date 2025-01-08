package itmo.coursework.services;

import itmo.coursework.dto.*;
import itmo.coursework.exceptions.entity.impl.*;
import itmo.coursework.model.entity.*;
import itmo.coursework.model.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class GameEventService {

    private final GameEventRepository gameEventRepository;
    private final GameRepository gameRepository;
    private final GameService gameService;
    private final LocationService locationService;
    private final LocationRepository locationRepository;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final EventStatusService eventStatusService;
    private final EventStatusRepository eventStatusRepository;
    private final SecurityService securityService;
    private final GameEventProfilesRepository gameEventProfilesRepository;


    public GameEventService(GameEventRepository gameEventRepository,
                            GameRepository gameRepository,
                            GameService gameService,
                            LocationService locationService,
                            LocationRepository locationRepository,
                            ProfileService profileService,
                            ProfileRepository profileRepository,
                            EventStatusService eventStatusService,
                            EventStatusRepository eventStatusRepository,
                            SecurityService securityService,
                            GameEventProfilesRepository gameEventProfilesRepository) {
        this.gameEventRepository = gameEventRepository;
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.locationService = locationService;
        this.locationRepository = locationRepository;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.eventStatusService = eventStatusService;
        this.eventStatusRepository = eventStatusRepository;
        this.securityService = securityService;
        this.gameEventProfilesRepository = gameEventProfilesRepository;
    }


    public Page<GameEventResponseDTO> getAllGameEvents(Pageable pageable) {
        return gameEventRepository.findAll(pageable).map(this::getDTOFromGameEvent);
    }


    public GameEventResponseDTO getGameEventById(Long id) {
        return gameEventRepository.findById(id).map(this::getDTOFromGameEvent)
                .orElseThrow(() -> new GameEventExistenceException(
                        "GameEvent с id="
                        + id
                        + " не существует"
                ));
    }


    @Transactional
    public GameEventResponseDTO createGameEvent(GameEventMutationDTO gameEventMutationDTO) {
        GameEvent gameEvent = getGameEventFromDTO(gameEventMutationDTO);
        gameEvent.setCurrentMembers(1);
        gameEvent = gameEventRepository.save(gameEvent);

        return getDTOFromGameEvent(gameEvent);
    }


    @Transactional
    public GameEventResponseDTO updateGameEvent(Long id, GameEventMutationDTO gameEventMutationDTO) {
        GameEvent gameEvent = gameEventRepository.findById(id)
                .orElseThrow(() -> new GameEventExistenceException(
                        "GameEvent с id="
                                + id
                                + " не существует"
                ));

        GameEvent gameEventToUpdate = getGameEventFromDTO(gameEventMutationDTO);
        if (gameEvent.getOrganiser() == gameEventToUpdate.getOrganiser()) {
            gameEvent.setName(gameEventToUpdate.getName());
            gameEvent.setDescription(gameEventToUpdate.getDescription());
            gameEvent.setLocation(gameEventToUpdate.getLocation());
            gameEvent.setDate(gameEventToUpdate.getDate());
            gameEvent.setStatus(gameEventToUpdate.getStatus());
            gameEvent.setOrganiser(gameEventToUpdate.getOrganiser());
            gameEvent.setWinner(gameEventToUpdate.getWinner());
            gameEvent.setGame(gameEventToUpdate.getGame());
            gameEvent.setMinMembers(gameEventToUpdate.getMinMembers());
            gameEvent.setMaxMembers(gameEventToUpdate.getMaxMembers());

            GameEvent updatedGameEvent = gameEventRepository.save(gameEvent);
            return getDTOFromGameEvent(updatedGameEvent);
        }
        throw new GameEventExistenceException(
                "GameEvent с id="
                        + id
                        + "  принадлежащей вам не существует"
        );
    }

    @Transactional
    public void deleteGameEvent(Long id) {
        GameEvent gameEvent = gameEventRepository.findById(id)
                .orElseThrow(() -> new GameEventExistenceException(
                        "GameEvent с id="
                                + id
                                + " не существует"
                ));
        if (Objects.equals(gameEvent.getOrganiser().getName(), securityService.findUserName())) {
            gameEventRepository.deleteById(id);
        }
    }

    protected GameEventResponseDTO getDTOFromGameEvent(GameEvent gameEvent) {
        if (gameEvent.getGame() == null) {
            throw new GameExistenceException("Game не существует");
        }
        if (gameEvent.getWinner() == null) {
            throw new ProfileExistenceException("Profile победителя не существует");
        }
        if (gameEvent.getOrganiser() == null) {
            throw new ProfileExistenceException("Profile организатора не существует");
        }
        if (gameEvent.getStatus() == null) {
            throw new EventStatusExistenceException("Status не существует");
        }
        if (gameEvent.getLocation() == null) {
            throw new LocationExistenceException("Location не существует");
        }
        GameResponseDTO gameResponseDTO = gameService.getDTOFromGame(gameEvent.getGame());
        ProfileResponseDTO organiserResponseDTO = profileService.getDTOFromProfile(gameEvent.getOrganiser());
        ProfileResponseDTO winnerResponseDTO = profileService.getDTOFromProfile(gameEvent.getWinner());
        LocationResponseDTO locationResponseDTO = locationService.getDTOFromLocation(gameEvent.getLocation());
        EventStatusResponseDTO eventStatusResponseDTO = eventStatusService.getDTOFromEventStatus(gameEvent.getStatus());
        return new GameEventResponseDTO(
                gameEvent.getId(),
                gameEvent.getName(),
                gameEvent.getDescription(),
                gameEvent.getDate(),
                gameEvent.getCurrentMembers(),
                gameEvent.getMinMembers(),
                gameEvent.getMaxMembers(),
                winnerResponseDTO,
                organiserResponseDTO,
                locationResponseDTO,
                eventStatusResponseDTO,
                gameResponseDTO
        );
    }

    protected GameEvent getGameEventFromDTO(GameEventMutationDTO gameEventMutationDTO) {
        GameEvent gameEvent = new GameEvent();
        Profile organiser = profileRepository.findById(gameEventMutationDTO.organizerId())
                .orElseThrow(() -> new ProfileExistenceException(
                        "Profile огранизатора с id="
                        + gameEventMutationDTO.organizerId()
                        + " не существует"
                ));
        Profile winner = profileRepository.findById(gameEventMutationDTO.winnerId())
                .orElseThrow(() -> new ProfileExistenceException(
                        "Profile победителя с id="
                                + gameEventMutationDTO.winnerId()
                                + " не существует"
                ));
        Location location = locationRepository.findById(gameEventMutationDTO.locationId())
                .orElseThrow(() -> new LocationExistenceException(
                        "Location с id="
                        + gameEventMutationDTO.locationId()
                        + " не существует"
                ));
        Game game = gameRepository.findById(gameEventMutationDTO.gameId())
                .orElseThrow(() -> new GameExistenceException(
                        "Game с id="
                        + gameEventMutationDTO.gameId()
                        + " не существует"
                ));
        EventStatus status = eventStatusRepository.findById(gameEventMutationDTO.statusId())
                .orElseThrow(() -> new EventStatusExistenceException(
                        "EventStatus с id="
                        + gameEventMutationDTO.statusId()
                        + " не существует"
                ));
        gameEvent.setName(gameEventMutationDTO.name());
        gameEvent.setDescription(gameEventMutationDTO.description());
        gameEvent.setDate(gameEventMutationDTO.date());
        gameEvent.setMinMembers(gameEventMutationDTO.minMembers());
        gameEvent.setMaxMembers(gameEventMutationDTO.maxMembers());
        gameEvent.setOrganiser(organiser);
        gameEvent.setWinner(winner);
        gameEvent.setStatus(status);
        gameEvent.setLocation(location);
        gameEvent.setGame(game);

        return gameEvent;
    }
}
