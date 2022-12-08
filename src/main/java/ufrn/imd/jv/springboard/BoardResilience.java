package ufrn.imd.jv.springboard;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BoardResilience {
    private final UserServiceInterface userService;
    private final BoardRepository repository;

    @Autowired
    public BoardResilience(UserServiceInterface userService, BoardRepository repository) {
        this.userService = userService;
        this.repository = repository;
    }

    @CircuitBreaker(name = "isUserValid_cb", fallbackMethod = "isUserKnown")
    @Bulkhead(name = "isUserValid_bh", fallbackMethod = "isUserKnown")
    public boolean isUserValid(Long id) {
        ResponseEntity<Map<String, String>> response = userService.getUser(id);
        return response.getStatusCode().is2xxSuccessful();
    }
    public boolean isUserKnown(Long id, Throwable t) {
        System.err.println(
                "Não foi possível consultar o service de usuários devido a: " +
                        t.getMessage() +
                        "Consultando boards locais em busca do usuário"
        );
        if (repository.existsByUserId(id)) {
            System.err.println("Usuário foi encontrado, portanto é válido");
            return true;
        } else {
            System.err.println("Não foi encontrado board criado pelo usuário de id="+id);
            return false;
        }
    }
}
