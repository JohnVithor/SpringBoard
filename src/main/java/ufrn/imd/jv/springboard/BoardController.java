package ufrn.imd.jv.springboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "boards")
public class BoardController {

    private final BoardService service;

    @Autowired
    public BoardController(BoardService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BoardEntity> createBoard(@RequestBody BoardEntity boardEntity) {
        return ResponseEntity.ok(service.save(boardEntity));
    }

    @GetMapping
    public ResponseEntity<Page<BoardEntity>> getPage(
            @RequestParam(name = "pg", required = false) Optional<Integer> page,
            @RequestParam(name = "lim", required = false) Optional<Integer> limit) {
        return service.getPage(page.orElse(0), limit.orElse(10));
    }

    @GetMapping(path = "user/{id}")
    public ResponseEntity<Page<BoardEntity>> getByUserId(@PathVariable Long id,
                                                         @RequestParam(name = "pg", required = false) Optional<Integer> page,
                                                         @RequestParam(name = "lim", required = false) Optional<Integer> limit) {
        return service.getByUserId(id, page.orElse(0), limit.orElse(10));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<BoardEntity> getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
