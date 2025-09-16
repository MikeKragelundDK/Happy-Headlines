package DraftService.controllers;
import DraftService.Service.DraftService_I;
import DraftService.dto.DraftRequest;
import DraftService.entities.Draft;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/drafts")
public class DraftController {
        private final DraftService_I draftService;

    public DraftController(DraftService_I draftService) {
        this.draftService = draftService;
    }

    @GetMapping()
    public ResponseEntity<List<Draft>> getdrafts(){
        List<Draft> fetched = draftService.fetchDrafts();
        return ResponseEntity.status(HttpStatus.OK).body(fetched);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Draft> getDraft(@PathVariable long id){
        Draft fetched = draftService.fetchDraft(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetched);
    }

    @PostMapping()
    public ResponseEntity<Draft> addDraft(@RequestBody DraftRequest draft){
        Draft d = new Draft(draft.getTitle(), draft.getAuthor(), draft.getContent(), draft.getLastEditedAt());
        Draft saved = draftService.saveDraft(d);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Draft> deleteDraft(@PathVariable long id){
        Draft draft = draftService.fetchDraft(id);
        if(draft == null) {
            throw new RuntimeException("Draft not found: " + id);
        }
        draftService.deleteDraft(id);
        return ResponseEntity.status(HttpStatus.OK).body(draft);
    }
}
