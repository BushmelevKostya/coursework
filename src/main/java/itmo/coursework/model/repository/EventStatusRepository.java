package itmo.coursework.model.repository;

import itmo.coursework.model.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventStatusRepository extends JpaRepository<EventStatus, Long> {
}
