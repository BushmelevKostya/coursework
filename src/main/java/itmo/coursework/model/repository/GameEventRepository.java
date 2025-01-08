package itmo.coursework.model.repository;

import itmo.coursework.model.entity.GameEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameEventRepository extends JpaRepository<GameEvent, Long> {
	
	@Query(value = "SELECT ge.* FROM game_events ge " +
			"JOIN location loc ON ge.location_id = loc.id " +
			"JOIN district d ON loc.district_id = d.id " +
			"WHERE d.name = :districtName", nativeQuery = true)
	List<GameEvent> findByDistrict(@Param("districtName") String districtName);
	
	@Query(value = "SELECT ge.* FROM game_events ge " +
			"JOIN location loc ON ge.location_id = loc.id " +
			"JOIN district d ON loc.district_id = d.id " +
			"JOIN city c ON city_id = c.id " +
			"WHERE c.name = :cityName", nativeQuery = true)
	List<GameEvent> findByCity(@Param("cityName") String cityName);
	
	@Query(value = "SELECT ge.* FROM game_events ge " +
			"JOIN game g ON ge.game_id = g.id " +
			"WHERE g.name = :gameName", nativeQuery = true)
	List<GameEvent> findByGame(@Param("gameName") String gameName);
	
	@Query(value = "SELECT ge.* FROM game_events ge WHERE ge.min_members >= :minMembers", nativeQuery = true)
	List<GameEvent> findByMinimumMembers(@Param("minMembers") int minMembers);
	
	@Query(value = "SELECT ge.* FROM game_events ge " +
			"JOIN game g ON ge.game_id = g.id " +
			"JOIN game_genre gg ON g.id = gg.game_id " +
			"JOIN genre gn ON gg.genre_id = gn.id " +
			"WHERE gn.genre = :genre", nativeQuery = true)
	List<GameEvent> findByGameGenre(@Param("genre") String genre);
	
	@Query(value = "SELECT ge.* FROM game_events ge " +
			"JOIN event_status es ON ge.status_id = es.id " +
			"WHERE es.status = 'Проведено'", nativeQuery = true)
	List<GameEvent> findScheduledEvents();
}
