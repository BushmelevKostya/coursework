package itmo.coursework.model.entity;

import jakarta.persistence.*;

@Entity
public class OtherEventProfiles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "profileId")
	private Profile profile;
	
	@ManyToOne
	@JoinColumn(name = "otherEventId")
	private OtherEvent otherEvent;
	
}
