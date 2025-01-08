package itmo.coursework.model.repository;

import itmo.coursework.model.entity.GameEvent;
import itmo.coursework.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameEventRepository extends JpaRepository<GameEvent, Long> {
    Optional<GameEvent> findByIdAndOrganiser(Long id, Profile organiser);
}
