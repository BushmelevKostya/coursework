package itmo.coursework.api.controller;

import itmo.coursework.dto.LocationMutationDTO;
import itmo.coursework.dto.LocationResponseDTO;
import itmo.coursework.model.entity.Location;
import itmo.coursework.services.LocationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public Page<LocationResponseDTO> findAll(Pageable pageable) {
        return locationService.getAllLocations(pageable);
    }


    @GetMapping("/{id}")
    public LocationResponseDTO findById(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationResponseDTO createLocation(@RequestBody LocationMutationDTO locationMutationDTO) {
        return locationService.createLocation(locationMutationDTO);
    }


    @PutMapping("/{id}")
    public LocationResponseDTO updateLocation(@PathVariable Long id,
                                              @RequestBody LocationMutationDTO locationMutationDTO) {
        return locationService.updateLocation(id, locationMutationDTO);
    }
}
