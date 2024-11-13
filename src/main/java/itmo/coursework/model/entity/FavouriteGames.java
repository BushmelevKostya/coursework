package itmo.coursework.model.entity;

import jakarta.persistence.*;

@Entity
public class FavouriteGames {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "profileId")
	private Profile profile;
	
	@ManyToOne
	@JoinColumn(name = "gameId")
	private Game game;
	
}
