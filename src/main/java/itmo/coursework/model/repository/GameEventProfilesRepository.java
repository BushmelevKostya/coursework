package itmo.coursework.model.repository;

import itmo.coursework.model.entity.GameEvent;
import itmo.coursework.model.entity.GameEventProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameEventProfilesRepository extends JpaRepository<GameEventProfiles, Long> {
    long countByGameEvent(GameEvent gameEvent);
}
