package com.team.bang.note;

import com.team.bang.card.Card;
import com.team.bang.folder.Folder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Note {
    private Long id;
    private String name;
    private Folder folder;
    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
        card.setNote(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
