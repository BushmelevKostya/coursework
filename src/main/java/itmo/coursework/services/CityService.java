package itmo.coursework.services;

import itmo.coursework.dto.CityMutationDTO;
import itmo.coursework.dto.CityResponseDTO;
import itmo.coursework.exceptions.entity.impl.CityExistenceException;
import itmo.coursework.exceptions.entity.impl.GenreExistenceException;
import itmo.coursework.model.entity.City;
import itmo.coursework.model.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }


    public Page<CityResponseDTO> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(this::getDTOFromCity);
    }


    public CityResponseDTO getCityById(Long id) {
        return cityRepository.findById(id).map(this::getDTOFromCity)
                .orElseThrow(() -> new CityExistenceException("City с id=" + id + " не существует"));
    }


    //TODO admin method
    @Transactional
    public CityResponseDTO createCity(CityMutationDTO cityMutationDTO) {
        City city = cityRepository.save(getCityFromDTO(cityMutationDTO));
        return getDTOFromCity(city);
    }


    //TODO admin method
    @Transactional
    public CityResponseDTO updateCity(Long id, CityMutationDTO cityMutationDTO) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new CityExistenceException("City с id=" + id + " не существует"));
        city.setName(cityMutationDTO.name());
        City updatedCity = cityRepository.save(city);

        return getDTOFromCity(updatedCity);
    }


    protected CityResponseDTO getDTOFromCity(City city) {
        return new CityResponseDTO(city.getId(), city.getName());
    }

    protected City getCityFromDTO(CityMutationDTO cityMutationDTO) {
        City city = new City();
        city.setName(cityMutationDTO.name());
        return city;
    }
}
