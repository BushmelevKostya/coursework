package itmo.coursework.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "cityId")
	private City city;
	
	@OneToMany(mappedBy = "district")
	private Set<Location> locations;
}
