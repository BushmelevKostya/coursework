package itmo.coursework.model.repository;

import itmo.coursework.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByName(String name);
    @Query(value = "SELECT * FROM insert_profile(:name, :icon)", nativeQuery = true)
    Profile insertProfile(@Param("name") String name, @Param("icon") String icon);
}
