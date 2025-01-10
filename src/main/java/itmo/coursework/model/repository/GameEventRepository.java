package itmo.coursework.model.repository;

import itmo.coursework.model.entity.GameEvent;
import itmo.coursework.model.entity.GameHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
	
	// самая популярная игра из истории игр
	@Query("""
       SELECT g.id
       FROM GameHistory gh
       JOIN Profile p ON p.id = gh.profile.id
       JOIN GameEventProfiles gep ON gep.profile.id = p.id
       JOIN GameEvent ge ON ge.id = gep.gameEvent.id
       JOIN Game g ON g.id = ge.game.id
       GROUP BY g.id
       ORDER BY COUNT(gh.id) DESC
       LIMIT 1
       """)
	Optional<Long> findMostFrequentGameId();
	
	// FavouriteGames
	@Query("""
       SELECT fg.game.id
       FROM FavouriteGames fg
       WHERE fg.profile.id = :profileId
       """)
	List<Long> findFavouriteGameIdsByProfile(@Param("profileId") Long profileId);
	
	// Profiles других пользователей с похожими FavouriteGames
	@Query("""
       SELECT DISTINCT fg.profile.id
       FROM FavouriteGames fg
       WHERE fg.game.id IN :gameIds AND fg.profile.id != :profileId
       """)
	List<Long> findProfilesWithSimilarFavouriteGames(@Param("gameIds") List<Long> gameIds,
	                                                 @Param("profileId") Long profileId);
	
	// игры, которые есть у других пользователей, но отсутствуют у текущего
	@Query("""
       SELECT DISTINCT fg.game.id
       FROM FavouriteGames fg
       WHERE fg.profile.id IN :profileIds AND fg.game.id NOT IN :excludedGameIds
       """)
	List<Long> findRecommendedGameIds(@Param("profileIds") List<Long> profileIds,
	                                  @Param("excludedGameIds") List<Long> excludedGameIds);
	
	// события по играм
	@Query("""
       SELECT ge
       FROM GameEvent ge
       WHERE ge.game.id IN :gameIds
       """)
	List<GameEvent> findEventsByGameIds(@Param("gameIds") List<Long> gameIds);
}
