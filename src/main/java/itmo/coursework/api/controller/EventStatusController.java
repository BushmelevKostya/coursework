package itmo.coursework.api.controller;

import itmo.coursework.dto.EventStatusMutationDTO;
import itmo.coursework.dto.EventStatusResponseDTO;
import itmo.coursework.model.entity.EventStatus;
import itmo.coursework.services.EventStatusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/eventstatus")
public class EventStatusController {

    private final EventStatusService eventStatusService;

    public EventStatusController(EventStatusService eventStatusService) {
        this.eventStatusService = eventStatusService;
    }


    @GetMapping
    public Page<EventStatusResponseDTO> findAll(Pageable pageable) {
        return eventStatusService.getAllEventStatuses(pageable);
    }


    @GetMapping("/{id}")
    public EventStatusResponseDTO findEventStatusById(@PathVariable Long id) {
        return eventStatusService.getEventStatusById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventStatusResponseDTO createEventStatus(@RequestBody EventStatusMutationDTO eventStatusMutationDTO) {
        return eventStatusService.createEventStatus(eventStatusMutationDTO);
    }


    @PutMapping("/{id}")
    public EventStatusResponseDTO updateEventStatus(@PathVariable Long id,
                                                    @RequestBody EventStatusMutationDTO eventStatusMutationDTO) {
        return eventStatusService.updateEventStatus(id, eventStatusMutationDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventStatus(@PathVariable Long id) {
        eventStatusService.deleteEventStatus(id);
        return ResponseEntity.ok().build();
    }
}
