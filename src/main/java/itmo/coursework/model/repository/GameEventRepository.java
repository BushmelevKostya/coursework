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
}
