package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "otherEvents")
@NoArgsConstructor
@AllArgsConstructor
public class OtherEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private LocalDateTime date;
	
	@ManyToOne
	private Location location;
	
	@OneToMany(mappedBy = "otherEvent")
	private Set<OtherEventProfiles> otherEventProfiles;
	
}
