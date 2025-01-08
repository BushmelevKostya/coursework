package itmo.coursework.services;

import itmo.coursework.dto.GameResponseDTO;
import itmo.coursework.dto.ProfileMutationDTO;
import itmo.coursework.dto.ProfileResponseDTO;
import itmo.coursework.exceptions.entity.impl.GameExistenceException;
import itmo.coursework.exceptions.entity.impl.ProfileExistenceException;
import itmo.coursework.model.entity.Game;
import itmo.coursework.model.entity.Profile;
import itmo.coursework.model.repository.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }


    public Page<ProfileResponseDTO> getAllProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable).map(this::getDTOFromProfile);
    }


    public ProfileResponseDTO getProfileById(Long id) {
        return profileRepository.findById(id).map(this::getDTOFromProfile)
                .orElseThrow(() -> new ProfileExistenceException("Profile с id=" + id + " не существует"));
    }


    public ProfileResponseDTO createProfile(ProfileMutationDTO profileMutationDTO) {
        Profile profile = getProfileFromDTO(profileMutationDTO);
        profile = profileRepository.save(profile);

        return getDTOFromProfile(profile);
    }


    //TODO admin method
    @Transactional
    public ProfileResponseDTO updateProfile(Long id, ProfileMutationDTO profileMutationDTO) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileExistenceException("Profile с id=" + id + " не существует"));
        profile.setName(profileMutationDTO.name());
        profile.setEmail(profileMutationDTO.email());
        profile.setIcon(profileMutationDTO.icon());
        profile.setPassword(profileMutationDTO.password());
        Profile updatedProfile = profileRepository.save(profile);

        return getDTOFromProfile(updatedProfile);
    }

    //TODO подумать над логикой удаления

    protected ProfileResponseDTO getDTOFromProfile(Profile profile) {
        return new ProfileResponseDTO(
                profile.getId(),
                profile.getName(),
                profile.getEmail(),
                profile.getIcon(),
                profile.getPassword()
        );
    }

    protected Profile getProfileFromDTO(ProfileMutationDTO profileMutationDTO) {
        Profile profile = new Profile();
        profile.setName(profileMutationDTO.name());
        profile.setEmail(profileMutationDTO.email());
        profile.setIcon(profileMutationDTO.icon());
        profile.setPassword(profileMutationDTO.password());
        return profile;
    }
}
