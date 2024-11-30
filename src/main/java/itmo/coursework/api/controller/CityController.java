package itmo.coursework.api.controller;

import itmo.coursework.dto.CityMutationDTO;
import itmo.coursework.dto.CityResponseDTO;
import itmo.coursework.services.CityService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @GetMapping
    public Page<CityResponseDTO> findAll(Pageable pageable) {
        return cityService.getAllCities(pageable);
    }


    @GetMapping("/{id}")
    public CityResponseDTO findCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponseDTO createCity(@RequestBody CityMutationDTO cityMutationDTO) {
        return cityService.createCity(cityMutationDTO);
    }


    @PutMapping("/{id}")
    public CityResponseDTO updateCity(@PathVariable Long id, @RequestBody CityMutationDTO cityMutationDTO) {
        return cityService.updateCity(id, cityMutationDTO);
    }
}
