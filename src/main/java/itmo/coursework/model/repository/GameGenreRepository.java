package itmo.coursework.model.repository;

import itmo.coursework.model.entity.GameGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GameGenreRepository extends JpaRepository<GameGenre, Long> {
    @Query(value = "SELECT * FROM insert_game_genre(:gameId, :genreId)", nativeQuery = true)
    GameGenre insertGameGenre(Long gameId, Long genreId);
    boolean existsByGameIdAndGenreId(Long gameId, Long genreId);
}
