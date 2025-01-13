package itmo.coursework.model.repository;

import itmo.coursework.model.entity.OtherEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherEventRepository extends JpaRepository<OtherEvent, Long> {
    @Query(value = "SELECT * FROM insert_other_event(:name, :description, :date, :locationId)", nativeQuery = true)
    OtherEvent insertOtherEvent(@Param("name") String name,
                                @Param("description") String description,
                                @Param("date") String date,
                                @Param("locationId") Long locationId);
    @Query(value = "SELECT delete_other_event_recursively(:eventId)", nativeQuery = true)
    void deleteOtherEventRecursively(@Param("eventId") Long eventId);
}
