package itmo.coursework.model.repository;

import itmo.coursework.model.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query(value = "SELECT * FROM insert_location(:address, :districtId)", nativeQuery = true)
    Location insertLocation(@Param("address") String address, @Param("districtId") Long districtId);
}
