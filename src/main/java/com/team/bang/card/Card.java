package com.team.bang.card;

import com.team.bang.note.Note;
import lombok.*;

@Getter
@Setter
@ToString
public class Card {
    private Long id;
    private Note note;
    private String keyword;
    private String content;

    public Card() {
    }
}
