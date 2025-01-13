package itmo.coursework.model.repository;

import itmo.coursework.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    @Query(value = "SELECT * FROM insert_city(:cityName)", nativeQuery = true)
    City insertCity(String cityName);
}
