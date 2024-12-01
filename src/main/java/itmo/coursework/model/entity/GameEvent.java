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
	private Long winnerId;
	private Long organiserId;
	private Long locationId;

	@ManyToOne
	private EventStatus statusId;
	
	@ManyToOne
	@JoinColumn(name = "gameId")
	private Game game;
	
	@OneToMany(mappedBy = "gameEvent")
	private Set<GameEventProfiles> gameEventProfiles;
	
}
