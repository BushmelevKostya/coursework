package itmo.coursework.model.repository;

import itmo.coursework.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {
	
	Profile findByEmail(String email);
}
