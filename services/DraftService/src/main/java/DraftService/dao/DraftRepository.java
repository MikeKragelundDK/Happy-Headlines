package DraftService.dao;

import DraftService.entities.Draft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DraftRepository extends JpaRepository<Draft, Long> {
    @Query("select d from Draft d order by d.lastEditedAt" )
    List<Draft> findAll();

    Draft getDraftById(long id);
}