package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "gameGenre")
@NoArgsConstructor
@AllArgsConstructor
public class GameGenre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "gameId", nullable = false)
	private Game game;
	
	@ManyToOne
	@JoinColumn(name = "genreId", nullable = false)
	private Genre genre;
}
