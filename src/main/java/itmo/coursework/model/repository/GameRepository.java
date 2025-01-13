package itmo.coursework.model.repository;

import itmo.coursework.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    @Query(value = "SELECT * FROM insert_game(:name, :description, :minPlayers, :maxPlayers)", nativeQuery = true)
    Game insertGame(@Param("name") String name,
                    @Param("description") String description,
                    @Param("minPlayers") int minPlayers,
                    @Param("maxPlayers") int maxPlayers);
}
