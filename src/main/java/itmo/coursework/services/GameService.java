package itmo.coursework.services;

import itmo.coursework.dto.GameMutationDTO;
import itmo.coursework.dto.GameResponseDTO;
import itmo.coursework.exceptions.entity.impl.GameExistenceException;
import itmo.coursework.model.entity.Game;
import itmo.coursework.model.repository.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    public Page<GameResponseDTO> getAllGames(Pageable pageable) {
        return gameRepository.findAll(pageable).map(this::getDTOFromGame);
    }


    public GameResponseDTO getGameById(Long id) {
        return gameRepository.findById(id).map(this::getDTOFromGame)
                .orElseThrow(() -> new GameExistenceException("Game с id=" + id + " не существует"));
    }


    //TODO admin method
    @Transactional
    public GameResponseDTO createGame(GameMutationDTO gameMutationDTO) {
        Game game = getGameFromDTO(gameMutationDTO);
        game = gameRepository.save(game);

        return getDTOFromGame(game);
    }


    //TODO admin method
    @Transactional
    public GameResponseDTO updateGame(Long id, GameMutationDTO gameMutationDTO) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameExistenceException("Game с id=" + id + " не существует"));
        game.setName(gameMutationDTO.name());
        game.setDescription(gameMutationDTO.description());
        game.setMinPlayers(gameMutationDTO.minPlayers());
        game.setMaxPlayers(gameMutationDTO.maxPlayers());
        Game updatedGame = gameRepository.save(game);

        return getDTOFromGame(updatedGame);
    }

    //TODO подумать над логикой удаления

    protected GameResponseDTO getDTOFromGame(Game game) {
        return new GameResponseDTO(
                game.getId(),
                game.getName(),
                game.getDescription(),
                game.getMinPlayers(),
                game.getMaxPlayers()
        );
    }

    protected Game getGameFromDTO(GameMutationDTO gameMutationDTO) {
        Game game = new Game();
        game.setName(gameMutationDTO.name());
        game.setDescription(gameMutationDTO.description());
        game.setMinPlayers(gameMutationDTO.minPlayers());
        game.setMaxPlayers(gameMutationDTO.maxPlayers());
        return game;
    }
}
