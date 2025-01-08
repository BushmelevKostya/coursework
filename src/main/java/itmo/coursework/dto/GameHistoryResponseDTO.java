package itmo.coursework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record GameHistoryResponseDTO(Long id,
                                     String eventName,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                     LocalDateTime dateEvent,
                                     itmo.coursework.model.entity.GameResult gameResult,
                                     ProfileResponseDTO profileResponseDTO) {
}
