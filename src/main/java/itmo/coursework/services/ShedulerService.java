package itmo.coursework.services;

import itmo.coursework.model.entity.GameEvent;
import itmo.coursework.model.repository.GameEventRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;

import java.time.Duration;
import java.util.Date;

@Service
public class ShedulerService {
	private final ThreadPoolTaskScheduler scheduler;
	private final GameEventRepository gameEventRepository;
	
	public ShedulerService(GameEventRepository gameEventRepository) {
		this.scheduler = new ThreadPoolTaskScheduler();
		this.scheduler.setPoolSize(10);
		this.scheduler.initialize();
		this.gameEventRepository = gameEventRepository;
	}
	
	public ScheduledFuture<?> scheduleDeletion(GameEvent event, Duration delay) {
		return scheduler.schedule(() -> deleteGameEvent(event),
			new Date(System.currentTimeMillis() + delay.toMillis()));
//			new Date(System.currentTimeMillis() + 1000));
	}
	
	private void deleteGameEvent(GameEvent event) {
		gameEventRepository.delete(event);
	}
}
