package itmo.coursework.dto;

public record ProfileResponseDTO(Long id,
                                 String name,
                                 String email,
                                 String icon,
                                 String password) {
}
