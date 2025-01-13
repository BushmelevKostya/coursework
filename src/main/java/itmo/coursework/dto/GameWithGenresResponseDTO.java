package itmo.coursework.dto;

public record GameWithGenresResponseDTO (
        Long id,
        String name,
        String description,
        int minPlayers,
        int maxPlayers,
        String genres
){
}
