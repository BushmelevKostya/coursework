package itmo.coursework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record GameHistoryResponseDTO(Long id,
                                     String eventName,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                     ZonedDateTime dateEvent,
                                     String gameResult,
                                     ProfileResponseDTO profileResponseDTO) {
}
