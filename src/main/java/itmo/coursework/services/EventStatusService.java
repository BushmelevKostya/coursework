package itmo.coursework.services;

import itmo.coursework.dto.EventStatusMutationDTO;
import itmo.coursework.dto.EventStatusResponseDTO;
import itmo.coursework.exceptions.entity.impl.EventStatusExistenceException;
import itmo.coursework.model.entity.EventStatus;
import itmo.coursework.model.repository.EventStatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventStatusService {

    private final EventStatusRepository eventStatusRepository;

    public EventStatusService(EventStatusRepository eventStatusRepository) {
        this.eventStatusRepository = eventStatusRepository;
    }


    public Page<EventStatusResponseDTO> getAllEventStatuses(Pageable pageable) {
        return eventStatusRepository.findAll(pageable).map(this::getDTOFromEventStatus);
    }


    public EventStatusResponseDTO getEventStatusById(Long id) {
        return eventStatusRepository.findById(id).map(this::getDTOFromEventStatus)
                .orElseThrow(() -> new EventStatusExistenceException("EventStatus с id=" + id + "не существует"));
    }


    //TODO admin method
    @Transactional
    public EventStatusResponseDTO createEventStatus(EventStatusMutationDTO eventStatusMutationDTO) {
        EventStatus eventStatus = eventStatusRepository.save(getEventStatusFromDTO(eventStatusMutationDTO));
        return getDTOFromEventStatus(eventStatus);
    }


    //TODO admin method
    @Transactional
    public EventStatusResponseDTO updateEventStatus(Long id, EventStatusMutationDTO eventStatusMutationDTO) {
        EventStatus eventStatus = eventStatusRepository.findById(id)
                .orElseThrow(() -> new EventStatusExistenceException("EventStatus с id=" + id + "не существует"));
        eventStatus.setStatus(eventStatusMutationDTO.status());
        EventStatus updatedEventStatus = eventStatusRepository.save(eventStatus);

        return getDTOFromEventStatus(updatedEventStatus);
    }

    protected EventStatusResponseDTO getDTOFromEventStatus(EventStatus eventStatus) {
        return new EventStatusResponseDTO(eventStatus.getId(), eventStatus.getStatus());
    }

    protected EventStatus getEventStatusFromDTO(EventStatusMutationDTO eventStatusMutationDTO) {
        EventStatus eventStatus = new EventStatus();
        eventStatus.setStatus(eventStatusMutationDTO.status());
        return eventStatus;
    }
}
