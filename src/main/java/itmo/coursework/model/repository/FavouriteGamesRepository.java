package itmo.coursework.model.repository;

import itmo.coursework.model.entity.FavouriteGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface FavouriteGamesRepository extends JpaRepository<FavouriteGames, Long> {
	Optional<Integer> deleteByGameIdAndProfileId(Long gameId, Long profileId);
	
	@Query(value = "SELECT * FROM insert_favourite_game(:profileId, :gameId)", nativeQuery = true)
	FavouriteGames insertFavouriteGame(@Param("profileId") Long profileId, @Param("gameId") Long gameId);
	@Query("SELECT COUNT(f) > 0 FROM FavouriteGames f WHERE f.profile.id = :profileId AND f.game.id = :gameId")
	boolean existsByProfileIdAndGameId(@Param("profileId") Long profileId, @Param("gameId") Long gameId);
}
