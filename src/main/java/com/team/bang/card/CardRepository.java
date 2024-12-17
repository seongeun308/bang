package com.team.bang.card;

import java.util.List;
import java.util.Optional;

public interface CardRepository {
    Card save(Card card);
    Optional<Card> findById(long id);
    Card update(Card newCard);
    Card delete(long id);
    void deleteByNoteId(long noteId);
    List<Card> findByNoteId(long noteId);
}
