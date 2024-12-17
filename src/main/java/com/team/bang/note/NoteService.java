package com.team.bang.note;

import com.team.bang.card.Card;
import com.team.bang.card.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {
    private final NoteRepository noteRepository;
    private final CardService cardService;

    public Note add(Note note) {
        return noteRepository.save(note);
    }

    public void addCardToNote(Long noteId, Card added) {
        Note note = getNote(noteId);
        note.addCard(added);
        added.setNote(note);
    }

    public void deleteByFolderId(Long folderId) {
        List<Note> folders = noteRepository.findByFolderId(folderId);
        folders.forEach(note -> deleteNoteAndSub(note.getId()));
    }

    public Note deleteNoteAndSub(Long noteId) {
        Note note = noteRepository.delete(noteId);
        cardService.deleteByNoteId(note.getId());
        return note;
    }

    public Note getNote(long id) {
        return noteRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 카드입니다."));
    }

    public void modify(Note note) {
        noteRepository.update(note);
    }
}
