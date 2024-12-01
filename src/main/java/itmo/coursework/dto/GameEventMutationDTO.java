package itmo.coursework.dto;

import java.time.LocalDateTime;

public record GameEventMutationDTO(String name,
                                   String description,
                                   LocalDateTime date,
                                   int minMembers,
                                   int maxMembers,
                                   Long winnerId,
                                   Long organizerId,
                                   Long locationId,
                                   Long statusId,
                                   Long gameId) {
}
