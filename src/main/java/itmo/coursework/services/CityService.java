package itmo.coursework.services;

import itmo.coursework.dto.CityMutationDTO;
import itmo.coursework.dto.CityResponseDTO;
import itmo.coursework.exceptions.AuthorityException;
import itmo.coursework.exceptions.entity.impl.CityExistenceException;
import itmo.coursework.model.entity.City;
import itmo.coursework.model.repository.CityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final SecurityService securityService;

    public CityService(CityRepository cityRepository, SecurityService securityService) {
        this.cityRepository = cityRepository;
        this.securityService = securityService;
    }


    public Page<CityResponseDTO> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(this::getDTOFromCity);
    }


    public CityResponseDTO getCityById(Long id) {
        return cityRepository.findById(id).map(this::getDTOFromCity)
                .orElseThrow(() -> new CityExistenceException("City с id=" + id + " не существует"));
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CityResponseDTO createCity(CityMutationDTO cityMutationDTO) {
        City city = cityRepository.save(getCityFromDTO(cityMutationDTO));
        return getDTOFromCity(city);
    }



    @Transactional
    public CityResponseDTO updateCity(Long id, CityMutationDTO cityMutationDTO) {
        if (securityService.hasAdminRole()){
            City city = cityRepository.findById(id)
                    .orElseThrow(() -> new CityExistenceException("City с id=" + id + " не существует"));
            city.setName(cityMutationDTO.name());
            City updatedCity = cityRepository.save(city);

            return getDTOFromCity(updatedCity);
        }
        throw new AuthorityException("У вас недостаточно прав для изменения информации о городе");
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCity(Long id) {
        if (securityService.hasAdminRole()){
            cityRepository.deleteById(id);
        }
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
