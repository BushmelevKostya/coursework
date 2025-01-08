package itmo.coursework.model.repository;

import itmo.coursework.model.entity.FavouriteGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteGamesRepository extends JpaRepository<FavouriteGames, Long> {
}
