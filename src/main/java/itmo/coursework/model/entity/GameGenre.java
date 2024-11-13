package itmo.coursework.model.entity;

import jakarta.persistence.*;

@Entity
public class GameGenre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "gameId")
	private Game game;
	
	@ManyToOne
	@JoinColumn(name = "genreId")
	private Genre genre;
}
