package itmo.coursework.services;

import itmo.coursework.dto.DistrictResponseDTO;
import itmo.coursework.dto.LocationMutationDTO;
import itmo.coursework.dto.LocationResponseDTO;
import itmo.coursework.exceptions.entity.impl.DistrictExistenceException;
import itmo.coursework.exceptions.entity.impl.LocationExistenceException;
import itmo.coursework.model.entity.District;
import itmo.coursework.model.entity.Location;
import itmo.coursework.model.repository.DistrictRepository;
import itmo.coursework.model.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final DistrictService districtService;
    private final DistrictRepository districtRepository;


    public LocationService(LocationRepository locationRepository, DistrictService districtService, DistrictRepository districtRepository) {
        this.locationRepository = locationRepository;
        this.districtService = districtService;
        this.districtRepository = districtRepository;
    }


    public Page<LocationResponseDTO> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable).map(this::getDTOFromLocation);
    }


    public LocationResponseDTO getLocationById(Long id) {
        return locationRepository.findById(id).map(this::getDTOFromLocation)
                .orElseThrow(() -> new LocationExistenceException(
                        "Location с id="
                                + id
                                + " не существует"
                ));
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public LocationResponseDTO createLocation(LocationMutationDTO locationMutationDTO) {
        Location location = getLocationFromDTO(locationMutationDTO);
        location = locationRepository.save(location);

        return getDTOFromLocation(location);
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public LocationResponseDTO updateLocation(Long id, LocationMutationDTO locationMutationDTO) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new LocationExistenceException(
                        "Location с id="
                                + id
                                + " не существует"
                ));
        District district = districtRepository.findById(locationMutationDTO.districtId())
                .orElseThrow(() -> new DistrictExistenceException(
                        "District с id="
                                + locationMutationDTO.districtId()
                                + " не существует"
                ));
        location.setDistrict(district);
        location.setAddress(locationMutationDTO.address());
        Location updatedLocation = locationRepository.save(location);
        return getDTOFromLocation(updatedLocation);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    protected LocationResponseDTO getDTOFromLocation(Location location) {
        if (location.getDistrict() != null) {
            DistrictResponseDTO districtResponseDTO = districtService.getDTOFromDistrict(location.getDistrict());
            return new LocationResponseDTO(
                    location.getId(),
                    location.getAddress(),
                    districtResponseDTO
            );
        }
        return new LocationResponseDTO(location.getId(), location.getAddress(), null);
//        throw new DistrictExistenceException("District не существует");
    }

    protected Location getLocationFromDTO(LocationMutationDTO locationMutationDTO) {
        Location location = new Location();
        District district = districtRepository.findById(locationMutationDTO.districtId())
                .orElseThrow(() -> new DistrictExistenceException(
                        "District с id="
                                + locationMutationDTO.districtId()
                                + " не существует"

                ));
        location.setAddress(locationMutationDTO.address());
        location.setDistrict(district);

        return location;
    }
}
