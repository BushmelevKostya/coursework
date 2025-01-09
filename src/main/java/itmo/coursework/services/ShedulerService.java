package itmo.coursework.services;

import itmo.coursework.model.entity.*;
import itmo.coursework.model.repository.EventStatusRepository;
import itmo.coursework.model.repository.GameEventRepository;
import itmo.coursework.model.repository.GameHistoryRepository;
import itmo.coursework.model.repository.ProfileRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

import java.time.Duration;
import java.util.Date;

@Service
public class ShedulerService {
    private final ThreadPoolTaskScheduler scheduler;
    private final GameEventRepository gameEventRepository;
    private final GameHistoryRepository gameHistoryRepository;
    private final ProfileRepository profileRepository;
    private final EventStatusRepository eventStatusRepository;

    public ShedulerService(GameEventRepository gameEventRepository, GameHistoryRepository gameHistoryRepository, ProfileRepository profileRepository, EventStatusRepository eventStatusRepository) {
        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.setPoolSize(10);
        this.scheduler.initialize();
        this.gameEventRepository = gameEventRepository;
        this.gameHistoryRepository = gameHistoryRepository;
        this.profileRepository = profileRepository;
        this.eventStatusRepository = eventStatusRepository;
    }

    public ScheduledFuture<?> scheduleDeletion(GameEvent event, Duration delay) {
        return scheduler.schedule(() -> deleteGameEvent(event),
                //			new Date(System.currentTimeMillis() + delay.toMillis()));
                new Date(System.currentTimeMillis() + 1000));
    }

    public void deleteGameEvent(GameEvent event) {
        Set<GameEventProfiles> players = event.getGameEventProfiles();
        for (GameEventProfiles eventProfile : players) {
            GameHistory gameHistory = new GameHistory();
            gameHistory.setDateEvent(event.getDate());
            gameHistory.setEventName(event.getName());
            Profile profile = eventProfile.getProfile();
            gameHistory.setProfile(profile);
            gameHistoryRepository.save(gameHistory);

            Set<GameHistory> histories = profile.getGameHistories() != null ? profile.getGameHistories() : new HashSet<>();
            histories.add(gameHistory);
            profile.setGameHistories(histories);
            profileRepository.save(profile);
        }
        EventStatus eventStatus = new EventStatus();
        eventStatus.setStatus("Проведено");
        eventStatusRepository.save(eventStatus);
        eventStatusRepository.flush();
        event.setStatus(eventStatus);
        gameEventRepository.flush();
        gameEventRepository.save(event);
    }
}