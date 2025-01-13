package itmo.coursework.model.repository;

import itmo.coursework.model.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query(value = "SELECT * FROM insert_district(:districtName, :cityId)", nativeQuery = true)
    District insertDistrict(@Param("districtName") String districtName, @Param("cityId") Long cityId);
}

