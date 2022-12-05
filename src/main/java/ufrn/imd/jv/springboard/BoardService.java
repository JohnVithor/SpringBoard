package ufrn.imd.jv.springboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class BoardService {

    private final UserServiceInterface userService;

    private final BoardRepository repository;

    @Autowired
    public BoardService(UserServiceInterface service, BoardRepository repository) {
        this.userService = service;
        this.repository = repository;
    }

    public BoardEntity save(BoardEntity boardEntity) {
        if (boardEntity == null) {
            throw new RuntimeException("Entidade não informada");
        }
        if (boardEntity.getUserId() == null) {
            throw new RuntimeException("Usuário não informado");
        }
        ResponseEntity<Map<String, String>> response = userService.getUser(boardEntity.getUserId());
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Usuário informado não existe");
        }
        if (boardEntity.getName() == null) {
            throw new RuntimeException("Nome do board não informado");
        }
        if (boardEntity.getName().trim().equals("")) {
            throw new RuntimeException("Nome do board informado é inválido");
        }
        Optional<BoardEntity> optValue = repository.findByName(boardEntity.getName());
        if (optValue.isPresent()) {
            throw new RuntimeException("Nome do board já está em uso");
        }
        return repository.save(boardEntity);
    }

    public ResponseEntity<Page<BoardEntity>> getPage(int page, int limit) {
        return ResponseEntity.ok(repository.findAll(PageRequest.of(page, limit)));
    }

    public ResponseEntity<Page<BoardEntity>> getByUserId(Long id, int page, int limit) {
        return ResponseEntity.ok(repository.findByUserIdIs(id, PageRequest.of(page, limit)));
    }

    public ResponseEntity<BoardEntity> getById(Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }
}
