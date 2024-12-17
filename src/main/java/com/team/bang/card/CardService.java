package com.team.bang.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public Card add(Card card) {
        return cardRepository.save(card);
    }

    public Card get(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 카드입니다."));
    }

    public Card modify(Card card) {
        return cardRepository.update(card);
    }

    public void deleteByNoteId(Long noteId) {
        List<Card> cards = cardRepository.findByNoteId(noteId);
        cards.forEach(card -> cardRepository.deleteByNoteId(card.getId()));
    }

    public Card remove(long cardId) {
        return cardRepository.delete(cardId);
    }
}
