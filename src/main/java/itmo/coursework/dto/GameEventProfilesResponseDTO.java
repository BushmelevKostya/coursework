package itmo.coursework.dto;

public record GameEventProfilesResponseDTO(Long id,
                                           ProfileResponseDTO profileResponseDTO,
                                           GameEventResponseDTO gameEventResponseDTO) {
}
