package itmo.coursework.model.entity;

import jakarta.persistence.*;

@Entity
public class GameEventProfiles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "profileId")
	private Profile profile;
	
	@ManyToOne
	@JoinColumn(name = "gameId")
	private GameEvent gameEvent;
}
