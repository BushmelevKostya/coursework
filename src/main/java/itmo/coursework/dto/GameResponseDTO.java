package itmo.coursework.dto;

public record GameResponseDTO(Long id,
                              String name,
                              String description,
                              int minPlayers,
                              int maxPlayers) {
}
