package DraftService.Service;

import DraftService.dao.DraftRepository;
import DraftService.entities.Draft;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DraftServiceImpl implements DraftService_I{
    private final DraftRepository draftRepository;

    public DraftServiceImpl(DraftRepository draftRepository) {
        this.draftRepository = draftRepository;
    }

    @Override
    public List<Draft> fetchDrafts() {
        return draftRepository.findAll();
    }

    @Override
    public Draft fetchDraft(long id) {
        return draftRepository.getDraftById(id);
    }

    @Override
    public Draft saveDraft(Draft draft) {
        return draftRepository.save(draft);
    }

    @Override
    public void deleteDraft(long id) {
        draftRepository.deleteById(id);
    }
}
