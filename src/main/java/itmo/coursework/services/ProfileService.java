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
    private final SecurityService securityService;

    public ProfileService(ProfileRepository profileRepository, SecurityService securityService) {
        this.profileRepository = profileRepository;
        this.securityService = securityService;
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
        profile.setName(securityService.findUserName());
        profile = profileRepository.save(profile);
        return getDTOFromProfile(profile);
    }


    @Transactional
    public ProfileResponseDTO updateProfile(Long id, ProfileMutationDTO profileMutationDTO) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileExistenceException("Profile с id=" + id + " не существует"));
        if (profile.getName().equals(securityService.findUserName()) || securityService.hasAdminRole()){
            profile.setName(securityService.findUserName());
            profile.setIcon(profileMutationDTO.icon());
            Profile updatedProfile = profileRepository.save(profile);

            return getDTOFromProfile(updatedProfile);
        }
       throw new ProfileExistenceException("Вашего Profile с id=" + id + " не существует");
    }

    public void deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ProfileExistenceException("Profile с id=" + id + " не существует"));
        if (profile.getName().equals(securityService.findUserName()) || securityService.hasAdminRole()){
            profileRepository.delete(profile);
        }
        throw new ProfileExistenceException("Вашего Profile с id=" + id + " не существует");
    }

    protected ProfileResponseDTO getDTOFromProfile(Profile profile) {
        return new ProfileResponseDTO(
                profile.getId(),
                profile.getName(),
                profile.getIcon()
        );
    }

    protected Profile getProfileFromDTO(ProfileMutationDTO profileMutationDTO) {
        Profile profile = new Profile();
        profile.setName(securityService.findUserName());
        profile.setIcon(profileMutationDTO.icon());
        return profile;
    }
}
