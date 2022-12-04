package ufrn.imd.jv.springboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class BoardService {

    @Value("${service.user}")
    private String userService;

    private final BoardRepository repository;

    private final RestTemplate restTemplate;

    @Autowired
    public BoardService(BoardRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public boolean entidadeEhValida(String path, Long id) {
        ResponseEntity<String> response = restTemplate.exchange(
                path+"/"+id,
                HttpMethod.GET,
                null,
                String.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public BoardEntity save(BoardEntity boardEntity) {
        if(boardEntity == null) {
            throw new RuntimeException("Entidade não informada");
        }
        if (boardEntity.getUserId() == null) {
            throw new RuntimeException("Usuário não informado");
        }
        if(!entidadeEhValida(userService, boardEntity.getUserId())) {
            throw new RuntimeException("Usuário informado não existe");
        }

        if (boardEntity.getName() == null) {
            throw new RuntimeException("Nome do board não informado");
        }
        if(!boardEntity.getName().trim().equals("")) {
            throw new RuntimeException("Nome do board informado é inválido");
        }
        Optional<BoardEntity> optValue = repository.findByName(boardEntity.getName());
        if(optValue.isPresent()) {
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
