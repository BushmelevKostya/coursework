package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "gameEvents")
@NoArgsConstructor
@AllArgsConstructor
public class GameEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private LocalDateTime date;
	private int minMembers;
	private int maxMembers;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile winner;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile organiser;
	@ManyToOne(cascade = CascadeType.ALL)
	private Location location;

	@ManyToOne(cascade = CascadeType.ALL)
	private EventStatus status;
	
	@ManyToOne
	@JoinColumn(name = "gameId", nullable = false)
	private Game game;
	
	@OneToMany(mappedBy = "gameEvent", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GameEventProfiles> gameEventProfiles;
	
}
