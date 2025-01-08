package itmo.coursework.services;

import itmo.coursework.dto.*;
import itmo.coursework.exceptions.entity.impl.*;
import itmo.coursework.model.entity.*;
import itmo.coursework.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

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
    private final ShedulerService schedulerService;


    public GameEventService(GameEventRepository gameEventRepository,
                            GameRepository gameRepository,
                            GameService gameService,
                            LocationService locationService,
                            LocationRepository locationRepository,
                            ProfileService profileService,
                            ProfileRepository profileRepository,
                            EventStatusService eventStatusService,
                            EventStatusRepository eventStatusRepository, ShedulerService schedulerService) {
        this.gameEventRepository = gameEventRepository;
        this.gameRepository = gameRepository;
        this.gameService = gameService;
        this.locationService = locationService;
        this.locationRepository = locationRepository;
        this.profileService = profileService;
        this.profileRepository = profileRepository;
        this.eventStatusService = eventStatusService;
        this.eventStatusRepository = eventStatusRepository;
	    this.schedulerService = schedulerService;
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

    //Событие удалится, когда истечет срок записи (gameevent.date)
    //TODO admin method
    @Transactional
    public GameEventResponseDTO createGameEvent(GameEventMutationDTO gameEventMutationDTO) {
        GameEvent gameEvent = getGameEventFromDTO(gameEventMutationDTO);
        gameEvent = gameEventRepository.save(gameEvent);
//        this.schedulerService.scheduleDeletion(gameEvent, Duration.between(LocalDateTime.now(), gameEvent.getDate()));
        return getDTOFromGameEvent(gameEvent);
    }


    //TODO admin method
    @Transactional
    public GameEventResponseDTO updateGameEvent(Long id, GameEventMutationDTO gameEventMutationDTO) {
        GameEvent gameEvent = gameEventRepository.findById(id)
                .orElseThrow(() -> new GameEventExistenceException(
                        "GameEvent с id="
                                + id
                                + " не существует"
                ));
        GameEvent gameEventToUpdate = getGameEventFromDTO(gameEventMutationDTO);
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

    public GameEventResponseDTO getDTOFromGameEvent(GameEvent gameEvent) {
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
        System.out.println(gameEventMutationDTO.organizerId());
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
    
    public String deleteGameEvent(Long id, GameEventMutationDTO gameEventMutationDTO) {
        GameEvent gameEvent = gameEventRepository.findById(id)
                .orElseThrow(() -> new GameEventExistenceException(
                        "GameEvent с id="
                                + id
                                + " не существует"
                ));
        schedulerService.deleteGameEvent(gameEvent);
        return "успешно удалено";
    }
}
