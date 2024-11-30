package itmo.coursework.api.controller;

import itmo.coursework.dto.DistrictMutationDTO;
import itmo.coursework.dto.DistrictResponseDTO;
import itmo.coursework.services.DistrictService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/district")
public class DistrictController {

    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }


    @GetMapping
    public Page<DistrictResponseDTO> findAll(Pageable pageable) {
        return districtService.getAllDistricts(pageable);
    }


    @GetMapping("/{id}")
    public DistrictResponseDTO findDistrictById(@PathVariable Long id) {
        return districtService.getDistrictById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DistrictResponseDTO createDistrict(@RequestBody DistrictMutationDTO districtMutationDTO) {
        return districtService.createDistrict(districtMutationDTO);
    }


    @PutMapping("/{id}")
    public DistrictResponseDTO updateDistrict(@PathVariable Long id,
                                              @RequestBody DistrictMutationDTO districtMutationDTO) {
        return districtService.updateDistrict(id, districtMutationDTO);
    }
}
