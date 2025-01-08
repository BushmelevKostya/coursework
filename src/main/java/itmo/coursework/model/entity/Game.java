package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "game")
@NoArgsConstructor
@AllArgsConstructor
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private int minPlayers;
	private int maxPlayers;
	
	@ManyToMany(mappedBy = "favouriteGames")
	private Set<Profile> profiles;
	
	@OneToMany(mappedBy = "game")
	private Set<GameEvent> gameEvents;
	
	@OneToMany(mappedBy = "game")
	private Set<GameGenre> gameGenres;
}
