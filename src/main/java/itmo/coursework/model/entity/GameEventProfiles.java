package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "gameEventProfiles")
@NoArgsConstructor
@AllArgsConstructor
public class GameEventProfiles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "profileId")
	private Profile profile;
	
	@ManyToOne
	@JoinColumn(name = "gameId")
	private GameEvent gameEvent;
}
