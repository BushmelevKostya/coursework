package itmo.coursework.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record OtherEventResponseDTO(Long id,
                                    String name,
                                    String description,
                                    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
                                    LocalDateTime date,
                                    LocationResponseDTO locationResponseDTO
                                    ) {
}
