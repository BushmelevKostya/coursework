package itmo.coursework.dto;

public record LocationResponseDTO(Long id,
                                  String address,
                                  DistrictResponseDTO districtResponseDTO) {
}
