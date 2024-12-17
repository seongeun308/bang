package com.team.bang.note;

import com.team.bang.folder.Folder;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    Note save(Note note);
    Optional<Note> findById(long noteId);
    List<Note> findByFolderId(long folderId);
    void update(Note note);
    Note delete(long id);
}
