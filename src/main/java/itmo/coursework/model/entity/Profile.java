package itmo.coursework.model.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String email;
	private String icon;
	
	@ManyToMany
	@JoinTable(
		name = "favourite_games",
		joinColumns = @JoinColumn(
			name = "profile_id"),
		inverseJoinColumns = @JoinColumn(
			name = "game_id"))
	private Set<Game> favouriteGames;
	
	@OneToMany(mappedBy = "profile")
	private Set<GameHistory> gameHistories;
	
	@OneToMany(mappedBy = "profile")
	private Set<GameEventProfiles> gameEventProfiles;
	
	@OneToMany(mappedBy = "profile")
	private Set<OtherEventProfiles> otherEventProfiles;
	
	
}

