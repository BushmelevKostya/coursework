package itmo.coursework.services;

import itmo.coursework.dto.CityResponseDTO;
import itmo.coursework.dto.DistrictMutationDTO;
import itmo.coursework.dto.DistrictResponseDTO;
import itmo.coursework.exceptions.entity.impl.CityExistenceException;
import itmo.coursework.exceptions.entity.impl.DistrictExistenceException;
import itmo.coursework.exceptions.entity.impl.GameHistoryExistenceException;
import itmo.coursework.model.entity.City;
import itmo.coursework.model.entity.District;
import itmo.coursework.model.repository.CityRepository;
import itmo.coursework.model.repository.DistrictRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final CityService cityService;
    private final CityRepository cityRepository;

    public DistrictService(DistrictRepository districtRepository, CityService cityService, CityRepository cityRepository) {
        this.districtRepository = districtRepository;
        this.cityService = cityService;
        this.cityRepository = cityRepository;
    }


    public Page<DistrictResponseDTO> getAllDistricts(Pageable pageable) {
        return districtRepository.findAll(pageable).map(this::getDTOFromDistrict);
    }


    public DistrictResponseDTO getDistrictById(Long id) {
        return districtRepository.findById(id).map(this::getDTOFromDistrict)
                .orElseThrow(() -> new DistrictExistenceException(
                        "District с id="
                            + id
                            + " не существует"
                ));
    }


    //TODO admin method
    @Transactional
    public DistrictResponseDTO createDistrict(DistrictMutationDTO districtMutationDTO) {
        District district = getDistrictFromDTO(districtMutationDTO);
        district = districtRepository.save(district);

        return getDTOFromDistrict(district);
    }


    //TODO admin method
    @Transactional
    public DistrictResponseDTO updateDistrict(Long id, DistrictMutationDTO districtMutationDTO) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new DistrictExistenceException(
                        "District с id="
                                + id
                                + " не существует"
                ));
        City city = cityRepository.findById(districtMutationDTO.cityId())
                .orElseThrow(() -> new CityExistenceException("City с id=" + id + " не существует"));
        district.setName(districtMutationDTO.name());
        district.setCity(city);
        District updatedDistrict = districtRepository.save(district);
        return getDTOFromDistrict(updatedDistrict);

    }


    protected DistrictResponseDTO getDTOFromDistrict(District district) {
        if (district.getCity() != null) {
            CityResponseDTO cityResponseDTO = cityService.getDTOFromCity(district.getCity());
            return new DistrictResponseDTO(
                    district.getId(),
                    district.getName(),
                    cityResponseDTO
            );
        }
        throw new CityExistenceException("City не существует");
    }

    protected District getDistrictFromDTO(DistrictMutationDTO districtMutationDTO) {
        District district = new District();
        City city = cityRepository.findById(districtMutationDTO.cityId())
                .orElseThrow(() -> new GameHistoryExistenceException(
                        "City с id="
                                + districtMutationDTO.cityId()
                                + " не существует"
                ));
        district.setName(districtMutationDTO.name());
        district.setCity(city);

        return district;
    }
}
