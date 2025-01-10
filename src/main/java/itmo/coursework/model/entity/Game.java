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
@Table(name = "game", indexes = {
		@Index(name = "idx_game_name", columnList = "name")
})
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
	
	@OneToMany(mappedBy = "game",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<FavouriteGames> favouriteGames;
	
	@OneToMany(mappedBy = "game",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GameEvent> gameEvents;
	
	@OneToMany(mappedBy = "game",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GameGenre> gameGenres;
}
