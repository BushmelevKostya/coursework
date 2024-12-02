package itmo.coursework.api.controller;

import itmo.coursework.dto.OtherEventMutationDTO;
import itmo.coursework.dto.OtherEventResponseDTO;
import itmo.coursework.model.entity.OtherEvent;
import itmo.coursework.services.OtherEventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otherevent")
public class OtherEventController {

    private final OtherEventService otherEventService;

    public OtherEventController(OtherEventService otherEventService) {
        this.otherEventService = otherEventService;
    }


    @GetMapping
    public Page<OtherEventResponseDTO> findAll(Pageable pageable) {
        return otherEventService.getAllOtherEvents(pageable);
    }


    @GetMapping("/{id}")
    public OtherEventResponseDTO findOtherEventById(@PathVariable Long id) {
        return otherEventService.getOtherEventById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OtherEventResponseDTO createOtherEvent(@RequestBody OtherEventMutationDTO otherEventMutationDTO) {
        return otherEventService.createOtherEvent(otherEventMutationDTO);
    }


    @PutMapping("/{id}")
    public OtherEventResponseDTO updateOtherEvent(@PathVariable Long id, @RequestBody OtherEventMutationDTO otherEventMutationDTO) {
        return otherEventService.updateOtherEvent(id, otherEventMutationDTO);
    }
}
