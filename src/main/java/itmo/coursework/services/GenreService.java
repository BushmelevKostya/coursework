package itmo.coursework.services;

import itmo.coursework.dto.GenreMutationDTO;
import itmo.coursework.dto.GenreResponseDTO;
import itmo.coursework.exceptions.entity.impl.GenreExistenceException;
import itmo.coursework.model.entity.Genre;
import itmo.coursework.model.repository.GenreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenreService {

    private final GenreRepository genreRepository;
//    private final SecurityService securityService;

    public GenreService(GenreRepository genreRepository) {
//    public GenreService(GenreRepository genreRepository, SecurityService securityService) {
        this.genreRepository = genreRepository;
//        this.securityService = securityService;
    }


    public Page<GenreResponseDTO> getAllGenres(Pageable pageable) {
        return genreRepository.findAll(pageable).map(this::getDTOFromGenre);
    }


    public GenreResponseDTO getGenreById(Long id) {
        return genreRepository.findById(id).map(this::getDTOFromGenre)
                .orElseThrow(() -> new GenreExistenceException("Genre с id=" + id + " не существует"));
    }


    //TODO admin method
    @Transactional
    public GenreResponseDTO createGenre(GenreMutationDTO genreMutationDTO) {
        Genre genre = getGenreFromDTO(genreMutationDTO);
        genre = genreRepository.save(genre);

        return getDTOFromGenre(genre);
    }


    //TODO admin method
    @Transactional
    public GenreResponseDTO updateGenre(Long id, GenreMutationDTO genreMutationDTO) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreExistenceException("Genre с id=" + id + " не существует"));
        genre.setName(genreMutationDTO.name());
        Genre updatedGenre = genreRepository.save(genre);

        return getDTOFromGenre(updatedGenre);
    }


    //TODO подумать над логикой удаления

    protected GenreResponseDTO getDTOFromGenre(Genre genre) {
        return new GenreResponseDTO(genre.getId(), genre.getName());
    }

    private Genre getGenreFromDTO(GenreMutationDTO genreMutationDTO) {
        Genre genre = new Genre();
        genre.setName(genreMutationDTO.name());
        return genre;
    }
}
