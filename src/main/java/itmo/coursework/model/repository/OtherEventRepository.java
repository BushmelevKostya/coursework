package itmo.coursework.model.repository;

import itmo.coursework.model.entity.OtherEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherEventRepository extends JpaRepository<OtherEvent, Long> {
}
