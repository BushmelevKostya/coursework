package itmo.coursework.services;

import itmo.coursework.dto.LocationResponseDTO;
import itmo.coursework.dto.OtherEventMutationDTO;
import itmo.coursework.dto.OtherEventResponseDTO;
import itmo.coursework.exceptions.entity.impl.GameEventExistenceException;
import itmo.coursework.exceptions.entity.impl.LocationExistenceException;
import itmo.coursework.exceptions.entity.impl.OtherEventExistenceException;
import itmo.coursework.model.entity.Location;
import itmo.coursework.model.entity.OtherEvent;
import itmo.coursework.model.repository.LocationRepository;
import itmo.coursework.model.repository.OtherEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OtherEventService {

    private final OtherEventRepository otherEventRepository;
    private final LocationRepository locationRepository;
    private final LocationService locationService;

    public OtherEventService(OtherEventRepository otherEventRepository, LocationRepository locationRepository, LocationService locationService) {
        this.otherEventRepository = otherEventRepository;
        this.locationRepository = locationRepository;
        this.locationService = locationService;
    }


    public Page<OtherEventResponseDTO> getAllOtherEvents(Pageable pageable) {
        return otherEventRepository.findAll(pageable).map(this::getDTOFromOtherEvent);
    }


    public OtherEventResponseDTO getOtherEventById(Long id) {
        return otherEventRepository.findById(id).map(this::getDTOFromOtherEvent)
                .orElseThrow(() -> new OtherEventExistenceException(
                        "OtherEvent с id="
                                + id
                                + " не существует"
                ));
    }


    //TODO admin method
    @Transactional
    public OtherEventResponseDTO createOtherEvent(OtherEventMutationDTO otherEventMutationDTO) {
        OtherEvent otherEvent = getOtherEventFromDTO(otherEventMutationDTO);
        otherEvent = otherEventRepository.save(otherEvent);

        return getDTOFromOtherEvent(otherEvent);
    }


    //TODO admin method
    @Transactional
    public OtherEventResponseDTO updateOtherEvent(Long id, OtherEventMutationDTO otherEventMutationDTO) {
        OtherEvent otherEvent = otherEventRepository.findById(id)
                .orElseThrow(() -> new OtherEventExistenceException(
                        "OtherEvent id="
                                + id
                                + " не существует"
                ));
        OtherEvent otherEventToUpdate = getOtherEventFromDTO(otherEventMutationDTO);
        otherEvent.setName(otherEventToUpdate.getName());
        otherEvent.setDescription(otherEventToUpdate.getDescription());
        otherEvent.setDate(otherEventToUpdate.getDate());
        otherEvent.setLocation(otherEventToUpdate.getLocation());

        OtherEvent updatedOtherEvent = otherEventRepository.save(otherEvent);
        return getDTOFromOtherEvent(updatedOtherEvent);
    }


    protected OtherEventResponseDTO getDTOFromOtherEvent(OtherEvent otherEvent) {
        if (otherEvent.getLocation() == null) {
            throw new LocationExistenceException("Location не существует");
        }
        LocationResponseDTO locationResponseDTO = locationService.getDTOFromLocation(otherEvent.getLocation());
        return new OtherEventResponseDTO(
                otherEvent.getId(),
                otherEvent.getName(),
                otherEvent.getDescription(),
                otherEvent.getDate(),
                locationResponseDTO
        );
    }

    protected OtherEvent getOtherEventFromDTO(OtherEventMutationDTO otherEventMutationDTO) {
        OtherEvent otherEvent = new OtherEvent();
        Location location = locationRepository.findById(otherEventMutationDTO.locationId())
                .orElseThrow(() -> new LocationExistenceException(
                        "Location с id="
                                + otherEventMutationDTO.locationId()
                                + " не существует"
                ));
        otherEvent.setName(otherEventMutationDTO.name());
        otherEvent.setDescription(otherEventMutationDTO.description());
        otherEvent.setDate(otherEventMutationDTO.date());
        otherEvent.setLocation(location);

        return otherEvent;
    }

}
