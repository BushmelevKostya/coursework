package itmo.coursework.dto;

public record DistrictResponseDTO(Long id,
                                  String name,
                                  CityResponseDTO cityResponseDTO) {
}
