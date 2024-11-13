package itmo.coursework.model.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private int minPlayers;
	private int maxPlayers;
	
	@OneToMany(mappedBy = "game")
	private Set<FavouriteGames> favouriteGames;
	
	@OneToMany(mappedBy = "game")
	private Set<GameEvent> gameEvents;
	
	@OneToMany(mappedBy = "game")
	private Set<GameGenre> gameGenres;
}