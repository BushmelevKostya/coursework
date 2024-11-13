package itmo.coursework.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Genre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "genre")
	private Set<GameGenre> gameGenres;

}
