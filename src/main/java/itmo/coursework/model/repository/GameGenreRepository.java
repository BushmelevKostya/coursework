package itmo.coursework.model.repository;

import itmo.coursework.model.entity.GameGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameGenreRepository extends JpaRepository<GameGenre, Long> {
}
