package com.team.bang.note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MemoryNoteRepository implements NoteRepository {
    private final Map<Long, Note> db = new HashMap<>();
    private static long id = 0L;

    @Override
    public Note save(Note note) {
        note.setId(++id);
        db.put(note.getId(), note);
        log.info("save note: {}", note);
        return note;
    }

    @Override
    public Optional<Note> findById(long id) {
        Note note = db.get(id);
        log.info("find note by id: {}", note);
        return Optional.ofNullable(note);
    }

    @Override
    public List<Note> findByFolderId(long folderId) {
        log.info("find notes by folder id: {}", folderId);
        return db.values().stream()
                .filter(n -> n.getFolder().getId() == folderId)
                .toList();
    }

    @Override
    public void update(Note note) {
        db.put(note.getId(), note);
        log.info("update note: {}", note);
    }

    @Override
    public Note delete(long id) {
        Note remove = db.remove(id);
        log.info("delete note: {}", remove);
        return remove;
    }


    @Override
    public List<Note> findAll() {
        log.info("find Note all");
        return new ArrayList<>(db.values());
    }

    public void clear() {
        db.clear();
    }
}
