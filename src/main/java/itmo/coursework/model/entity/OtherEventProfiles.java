package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "otherEventProfiles")
@NoArgsConstructor
@AllArgsConstructor
public class OtherEventProfiles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "profileId", nullable = false)
	private Profile profile;
	
	@ManyToOne
	@JoinColumn(name = "otherEventId", nullable = false)
	private OtherEvent otherEvent;
	
}
