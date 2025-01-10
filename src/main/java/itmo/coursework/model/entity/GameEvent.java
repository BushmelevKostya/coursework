package itmo.coursework.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
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
	private ZonedDateTime date;
	private int minMembers;
	@Positive
	private long currentMembers;
	private int maxMembers;
	@ManyToOne//(cascade = CascadeType.ALL)
	@JoinColumn(name = "winnerId")
	private Profile winner;
	@ManyToOne//(cascade = CascadeType.ALL)
	@JoinColumn(name = "organiserId")
	private Profile organiser;
	@ManyToOne//(cascade = CascadeType.ALL)
	private Location location;

	@ManyToOne//(cascade = CascadeType.ALL)
	private EventStatus status;
	
	@ManyToOne
	@JoinColumn(name = "gameId", nullable = false)
	private Game game;
	
	@OneToMany(mappedBy = "gameEvent")//, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GameEventProfiles> gameEventProfiles;
	
}
