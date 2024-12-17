package com.team.bang.card;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MemoryCardRepository implements CardRepository {
    private final Map<Long, Card> db = new HashMap<>();
    private static long id = 0L;

    @Override
    public Card save(Card card) {
        card.setId(++id);
        db.put(card.getId(), card);
        log.info("Save card: {}", card);
        return card;
    }

    @Override
    public Optional<Card> findById(long id) {
        Card card = db.get(id);
        log.info("Find card: {}", card);
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Card update(Card newCard) {
        db.put(newCard.getId(), newCard);
        log.info("Update card: {}", newCard);
        return findById(newCard.getId()).orElse(null);
    }

    @Override
    public Card delete(long id) {
        Card remove = db.remove(id);
        log.info("Delete card: {}", remove);
        return remove;
    }

    @Override
    public void deleteByNoteId(long noteId) {
        db.values().stream()
                .filter(card -> card.getNote().getId() == noteId)
                .forEach(card -> delete(card.getId()));
    }

    @Override
    public List<Card> findByNoteId(long noteId) {
        log.info("Find cards by note id: {}", noteId);
        return db.values().stream()
                .filter(card -> card.getNote().getId() == noteId)
                .toList();
    }

    public void clear() {
        db.clear();
    }
}
