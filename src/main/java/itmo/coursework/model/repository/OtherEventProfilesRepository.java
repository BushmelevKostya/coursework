package itmo.coursework.model.repository;

import itmo.coursework.model.entity.OtherEventProfiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherEventProfilesRepository extends JpaRepository<OtherEventProfiles, Long> {
}
