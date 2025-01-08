package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "gameHistory")
@NoArgsConstructor
@AllArgsConstructor
public class GameHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String eventName;
	private LocalDateTime dateEvent;
	
	@Enumerated(EnumType.STRING)
	private GameResult gameResult;
	
	@ManyToOne
	@JoinColumn(name = "profileId")
	private Profile profile;
}
