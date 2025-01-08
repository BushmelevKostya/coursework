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
@Table(name = "profile")
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String email;
	private String icon;
	
	@ManyToMany
	@JoinTable(
		name = "favourite_games",
		joinColumns = @JoinColumn(
			name = "profile_id"),
		inverseJoinColumns = @JoinColumn(
			name = "game_id"))
	private Set<Game> favouriteGames;
	
	@OneToMany(mappedBy = "profile")
	private Set<GameHistory> gameHistories;
	
	@OneToMany(mappedBy = "profile")
	private Set<GameEventProfiles> gameEventProfiles;
	
	@OneToMany(mappedBy = "profile")
	private Set<OtherEventProfiles> otherEventProfiles;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Set<Game> getFavouriteGames() {
		return favouriteGames;
	}
	
	public void setFavouriteGames(Set<Game> favouriteGames) {
		this.favouriteGames = favouriteGames;
	}
	
	public Set<GameHistory> getGameHistories() {
		return gameHistories;
	}
	
	public void setGameHistories(Set<GameHistory> gameHistories) {
		this.gameHistories = gameHistories;
	}
	
	public Set<GameEventProfiles> getGameEventProfiles() {
		return gameEventProfiles;
	}
	
	public void setGameEventProfiles(Set<GameEventProfiles> gameEventProfiles) {
		this.gameEventProfiles = gameEventProfiles;
	}
	
	public Set<OtherEventProfiles> getOtherEventProfiles() {
		return otherEventProfiles;
	}
	
	public void setOtherEventProfiles(Set<OtherEventProfiles> otherEventProfiles) {
		this.otherEventProfiles = otherEventProfiles;
	}
}

