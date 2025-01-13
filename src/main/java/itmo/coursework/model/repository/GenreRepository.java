package itmo.coursework.model.repository;

import itmo.coursework.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "SELECT * FROM insert_genre(:genreName)", nativeQuery = true)
    Genre insertGenre(String genreName);

    @Query("SELECT g FROM Genre g WHERE g.name = :name")
    Optional<Genre> findByName(String name);
}
