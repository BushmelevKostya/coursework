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
@Table(name = "profile")
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String email;
	private String icon;
	private String password;
	
	@OneToMany(mappedBy = "profile")
	private Set<FavouriteGames> favouriteGames;
	
	@OneToMany(mappedBy = "profile")
	private Set<GameHistory> gameHistories;
	
	@OneToMany(mappedBy = "profile")
	private Set<GameEventProfiles> gameEventProfiles;
	
	@OneToMany(mappedBy = "profile")
	private Set<OtherEventProfiles> otherEventProfiles;
	
	
}

