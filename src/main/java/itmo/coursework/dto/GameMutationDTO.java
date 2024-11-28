package itmo.coursework.dto;

public record GameMutationDTO(String name,
                              String description,
                              int minPlayers,
                              int maxPlayers) {
}
