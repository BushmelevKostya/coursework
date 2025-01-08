package itmo.coursework.api.controller;

import itmo.coursework.model.entity.GameEvent;
import itmo.coursework.model.repository.GameEventRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {
	
	@Autowired
	private GameEventRepository gameEventRepository;
	
	@PostMapping("/create")
	public GameEvent createEvent(@RequestBody @Valid GameEvent gameEvent) {
		System.out.println("Создание события: " + gameEvent);
		return gameEventRepository.save(gameEvent);
	}
}
