package itmo.coursework.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class GameHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String eventName;
	private LocalDateTime dateEvent;
	private GameResult gameResult;
	
	@ManyToOne
	@JoinColumn(name = "profileId")
	private Profile profile;
	
}
