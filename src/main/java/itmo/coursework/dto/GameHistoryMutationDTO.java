package itmo.coursework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record GameHistoryMutationDTO(String eventName,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                     LocalDateTime dateEvent,
                                     String gameResult,
                                     Long profileId) {
}
