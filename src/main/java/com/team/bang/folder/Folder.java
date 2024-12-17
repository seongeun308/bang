package com.team.bang.folder;

import com.team.bang.note.Note;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Folder {
    private long id;
    private String name;
    private Folder parent;
    private List<Folder> subFolders = new ArrayList<>();
    private List<Note> notes = new ArrayList<>();
    private LocalDateTime createdAt;

    public Folder(String name, Folder parent) {
        this.name = name;
        this.parent = parent;
        this.createdAt = LocalDateTime.now();
    }

    public void addNote(Note note) {
        notes.add(note);
        note.setFolder(this);
    }

    public void addSub(Folder folder) {
        subFolders.add(folder);
        folder.setParent(this);
    }

    public void deleteNote(Note note) {
        if (note != null) notes.remove(note);
    }

    public void deleteSub(Folder folder) {
        if (folder != null) subFolders.remove(folder);
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notes=" + notes +
                ", createdAt=" + createdAt +
                '}';
    }
}
