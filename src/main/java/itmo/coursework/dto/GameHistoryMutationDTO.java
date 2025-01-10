package itmo.coursework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record GameHistoryMutationDTO(String eventName,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
                                     ZonedDateTime dateEvent,
                                     String gameResult,
                                     Long profileId) {
}
