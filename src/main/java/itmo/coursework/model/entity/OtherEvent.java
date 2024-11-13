package itmo.coursework.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class OtherEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private LocalDateTime date;
	
	@ManyToOne
	@JoinColumn(name = "locationId")
	private Location location;
	
	@OneToMany(mappedBy = "otherEvent")
	private Set<OtherEventProfiles> otherEventProfiles;
	
}
