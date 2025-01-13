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
	@Column(columnDefinition = "TEXT")
	private String icon;
	
	@OneToMany(mappedBy = "profile",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<FavouriteGames> favouriteGames;
	
	@OneToMany(mappedBy = "profile",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GameHistory> gameHistories;
	
	@OneToMany(mappedBy = "profile",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<GameEventProfiles> gameEventProfiles;
	
	@OneToMany(mappedBy = "profile",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OtherEventProfiles> otherEventProfiles;
	
	
}

