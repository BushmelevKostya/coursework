package itmo.coursework.model.repository;

import itmo.coursework.model.entity.GameEvent;
import itmo.coursework.model.entity.GameEventProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameEventProfilesRepository extends JpaRepository<GameEventProfiles, Long> {
    long countByGameEvent(GameEvent gameEvent);
    @Query(value = "SELECT * FROM insert_game_event_profile(:profileId, :gameEventId)", nativeQuery = true)
    GameEventProfiles insertGameEventProfile(@Param("profileId") Long profileId, @Param("gameEventId") Long gameEventId);
    boolean existsByProfileIdAndGameEventId(Long profileId, Long gameEventId);
}