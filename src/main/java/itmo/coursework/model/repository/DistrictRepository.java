package itmo.coursework.model.repository;

import itmo.coursework.model.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
}
