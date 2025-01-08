package itmo.coursework.services;

import itmo.coursework.dto.OtherEventProfilesMutationDTO;
import itmo.coursework.dto.OtherEventProfilesResponseDTO;
import itmo.coursework.dto.OtherEventResponseDTO;
import itmo.coursework.dto.ProfileResponseDTO;
import itmo.coursework.exceptions.entity.impl.OtherEventExistenceException;
import itmo.coursework.exceptions.entity.impl.OtherEventProfilesExistenceException;
import itmo.coursework.exceptions.entity.impl.ProfileExistenceException;
import itmo.coursework.model.entity.OtherEvent;
import itmo.coursework.model.entity.OtherEventProfiles;
import itmo.coursework.model.entity.Profile;
import itmo.coursework.model.repository.OtherEventProfilesRepository;
import itmo.coursework.model.repository.OtherEventRepository;
import itmo.coursework.model.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OtherEventProfilesService {

    private final OtherEventProfilesRepository otherEventProfilesRepository;
    private final OtherEventService otherEventService;
    private final ProfileService profileService;
    private final OtherEventRepository otherEventRepository;
    private final ProfileRepository profileRepository;

    public OtherEventProfilesService(OtherEventProfilesRepository otherEventProfilesRepository, OtherEventService otherEventService, ProfileService profileService, OtherEventRepository otherEventRepository, ProfileRepository profileRepository) {
        this.otherEventProfilesRepository = otherEventProfilesRepository;
        this.otherEventService = otherEventService;
        this.profileService = profileService;
        this.otherEventRepository = otherEventRepository;
        this.profileRepository = profileRepository;
    }


    public Page<OtherEventProfilesResponseDTO> getAllOtherEventProfiles(Pageable pageable) {
        return otherEventProfilesRepository.findAll(pageable).map(this::getDTOFromOtherEventProfiles);
    }


    public OtherEventProfilesResponseDTO getOtherEventProfiles(Long id) {
        return otherEventProfilesRepository.findById(id).map(this::getDTOFromOtherEventProfiles)
                .orElseThrow(() -> new OtherEventProfilesExistenceException("OtherEventProfiles с id=" + id + " не существует"));
    }


    //TODO admin method
    @Transactional
    public OtherEventProfilesResponseDTO createOtherEventProfile(OtherEventProfilesMutationDTO otherEventProfilesMutationDTO) {
        OtherEventProfiles otherEventProfiles = getOtherEventProfilesFromDTO(otherEventProfilesMutationDTO);
        otherEventProfilesRepository.save(otherEventProfiles);

        return getDTOFromOtherEventProfiles(otherEventProfiles);
    }


    //TODO admin method
    @Transactional
    public OtherEventProfilesResponseDTO updateOtherEventProfile(Long id, OtherEventProfilesMutationDTO otherEventProfilesMutationDTO) {
        OtherEventProfiles otherEventProfiles = otherEventProfilesRepository.findById(id)
                .orElseThrow(() -> new OtherEventProfilesExistenceException("OtherEventProfiles с id=" + id + " не существует"));
        OtherEvent otherEvent = otherEventRepository.findById(otherEventProfilesMutationDTO.eventId())
                .orElseThrow(() -> new OtherEventExistenceException(
                        "OtherEvent с id="
                        + otherEventProfilesMutationDTO.eventId()
                        + " не существует"));
        Profile profile = profileRepository.findById(otherEventProfilesMutationDTO.profileId())
                .orElseThrow(() -> new ProfileExistenceException(
                        "Profile с id="
                        + otherEventProfilesMutationDTO.profileId()
                        + " не сущетсвует"));
        otherEventProfiles.setOtherEvent(otherEvent);
        otherEventProfiles.setProfile(profile);

        otherEventProfiles = otherEventProfilesRepository.save(otherEventProfiles);

        return getDTOFromOtherEventProfiles(otherEventProfiles);
    }

    private OtherEventProfilesResponseDTO getDTOFromOtherEventProfiles(OtherEventProfiles otherEventProfiles) {
        if (otherEventProfiles.getOtherEvent() == null) {
            throw new OtherEventExistenceException("Other event не существует");
        }
        if (otherEventProfiles.getProfile() == null) {
            throw new ProfileExistenceException("Profile не существует");
        }

        OtherEventResponseDTO otherEventResponseDTO = otherEventService.getDTOFromOtherEvent(otherEventProfiles.getOtherEvent());
        ProfileResponseDTO profileResponseDTO = profileService.getDTOFromProfile(otherEventProfiles.getProfile());

        return new OtherEventProfilesResponseDTO(
                otherEventProfiles.getId(),
                profileResponseDTO,
                otherEventResponseDTO
        );
    }

    private OtherEventProfiles getOtherEventProfilesFromDTO(OtherEventProfilesMutationDTO otherEventProfilesMutationDTO) {
        OtherEventProfiles otherEventProfiles = new OtherEventProfiles();
        OtherEvent otherEvent = otherEventRepository.findById(otherEventProfilesMutationDTO.eventId())
                .orElseThrow(() -> new OtherEventExistenceException(
                        "OtherEvent с id="
                        + otherEventProfilesMutationDTO.eventId()
                        + " не существует"
                ));
        Profile profile = profileRepository.findById(otherEventProfilesMutationDTO.profileId())
                .orElseThrow(() -> new ProfileExistenceException(
                        "Profile с id="
                        + otherEventProfilesMutationDTO.profileId()
                        + " не существует"
                ));
        otherEventProfiles.setOtherEvent(otherEvent);
        otherEventProfiles.setProfile(profile);

        return otherEventProfiles;
    }
}
