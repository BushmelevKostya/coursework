package itmo.coursework.dto;

public record OtherEventProfilesResponseDTO(Long id,
                                           ProfileResponseDTO profileResponseDTO,
                                           OtherEventResponseDTO otherEventResponseDTO) {
}
