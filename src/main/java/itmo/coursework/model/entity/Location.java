package itmo.coursework.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "districtId")
	private District district;
	
	@OneToMany(mappedBy = "location")
	private Set<OtherEvent> otherEvents;
}
