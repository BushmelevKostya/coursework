package itmo.coursework.model.repository;

import itmo.coursework.model.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
    @Query(value = "SELECT * FROM insert_event_status(:statusName)", nativeQuery = true)
    EventStatus insertEventStatus(String statusName);
    @Query("SELECT es FROM EventStatus es WHERE es.status = :status")
    Optional<EventStatus> findByStatus(String status);
}
