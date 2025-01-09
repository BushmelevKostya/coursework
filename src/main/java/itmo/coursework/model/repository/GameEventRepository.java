package itmo.coursework.model.repository;

import itmo.coursework.model.entity.GameEvent;
import itmo.coursework.model.entity.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameEventRepository extends JpaRepository<GameEvent, Long> {
    Optional<GameEvent> findByIdAndOrganiser(Long id, Profile organiser);

    @Query("SELECT ge FROM GameEvent ge " +
            "LEFT JOIN ge.location location " +
            "LEFT JOIN ge.status status " +
            "LEFT JOIN ge.game game " +
            "WHERE (:name IS NULL OR ge.name LIKE %:name%) " +
            "AND (:description IS NULL OR ge.description LIKE %:description%) " +
            "AND (:gameName IS NULL OR game.name LIKE %:gameName%) " +
            "AND (:locationName IS NULL OR location.address LIKE %:locationName%) " +
            "AND (:statusName IS NULL OR status.status LIKE %:statusName%) " +
            "AND (:minMembers IS NULL OR ge.minMembers >= :minMembers) " +
            "AND (:maxMembers IS NULL OR ge.maxMembers <= :maxMembers) " )//+
            //"AND (:startDate IS NULL OR ge.date >= :startDate) ")// +
            //"AND (:endDate IS NULL OR ge.date <= :endDate)")
    Page<GameEvent> findByFilters(
            @Param("name") String name,
            @Param("description") String description,
            @Param("gameName") String gameName,
            @Param("locationName") String locationName,
            @Param("statusName") String statusName,
            @Param("minMembers") Integer minMembers,
            @Param("maxMembers") Integer maxMembers,
            //@Param("startDate") ZonedDateTime startDate,
            //@Param("endDate") String endDate,
            Pageable pageable);
    @Query("SELECT ge FROM GameEvent ge WHERE ge.maxMembers - ge.currentMembers > 0")
    Page<GameEvent> findAllWithAvailableSlots(Pageable pageable);

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
            "WHERE es.status = 'finished'", nativeQuery = true)
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
