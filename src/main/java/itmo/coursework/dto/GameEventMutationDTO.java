package itmo.coursework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record GameEventMutationDTO(String name,
                                   String description,
                                   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
                                   ZonedDateTime date,
                                   int minMembers,
                                   int maxMembers,
                                   Long winnerId,
                                   Long organizerId,
                                   Long locationId,
                                   Long statusId,
                                   Long gameId) {
}
