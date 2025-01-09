package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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
	private ZonedDateTime dateEvent;
	private String gameResult;
	
	@ManyToOne
	@JoinColumn(name = "profileId", nullable = false)
	private Profile profile;
	
}
