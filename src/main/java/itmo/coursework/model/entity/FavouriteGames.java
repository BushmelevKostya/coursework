package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "favouriteGames")
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteGames {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "profileId", nullable = false)
	private Profile profile;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "gameId", nullable = false)
	private Game game;
	
}
