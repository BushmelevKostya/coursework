package itmo.coursework.model.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class City {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "city")
	private Set<District> districts;
}

