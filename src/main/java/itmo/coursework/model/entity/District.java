package itmo.coursework.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "district", indexes = {
		@Index(name = "idx_district_name", columnList = "name")
})
@NoArgsConstructor
@AllArgsConstructor
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cityId", nullable = false)
	private City city;
	
	@OneToMany(mappedBy = "district",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Location> locations;
}
