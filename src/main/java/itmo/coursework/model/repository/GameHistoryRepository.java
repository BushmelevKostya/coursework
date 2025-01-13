package itmo.coursework.model.repository;

import itmo.coursework.model.entity.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    @Query(value = "SELECT * FROM insert_game_history(:eventName, :dateEvent, :gameResult, :profileId) LIMIT 1", nativeQuery = true)
    GameHistory insertGameHistory(@Param("eventName") String eventName,
                                  @Param("dateEvent") ZonedDateTime dateEvent,
                                  @Param("gameResult") String gameResult,
                                  @Param("profileId") Long profileId);
}
