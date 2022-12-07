package ufrn.imd.jv.springboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    Page<BoardEntity> findByUserIdIs(Long id, PageRequest pageRequest);

    Optional<BoardEntity> findByName(String name);

    boolean existsByUserId(Long id);
}
