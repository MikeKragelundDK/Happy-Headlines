package DraftService.Service;

import DraftService.entities.Draft;

import java.util.List;

public interface DraftService_I {
  List<Draft> fetchDrafts();
  Draft fetchDraft(long id);
  Draft saveDraft(Draft draft);
  void deleteDraft(long id);
}