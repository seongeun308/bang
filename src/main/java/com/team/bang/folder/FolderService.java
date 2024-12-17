package com.team.bang.folder;

import com.team.bang.note.Note;
import com.team.bang.note.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FolderService {
    private final FolderRepository folderRepository;
    private final NoteService noteService;

    public void add(Folder folder) {
        folderRepository.save(folder);
    }

    public void addFolderToFolder(Folder folder, long parentId) {
        Folder parent = getFolder(parentId);
        folder.setParent(parent);
        Folder saved = folderRepository.save(folder);
        parent.addSub(saved);
    }

    public void addNoteToFolder(Long folderId, Note added) {
        Folder folder = getFolder(folderId);
        folder.addNote(added);
    }

    public List<Folder> getTopLevelFolders() {
        return folderRepository.findByParent(null);
    }

    public Folder getFolder(long id) {
        return folderRepository.findById(id).orElseThrow(() -> new RuntimeException("존재하지 않는 카드입니다."));
    }

    public void modify(Folder newFolder) {
        folderRepository.update(newFolder);
    }

    public void delete(long folderId) {
        Folder deleted = folderRepository.delete(folderId);
        Folder parent = deleted.getParent();
        if (parent != null)
            parent.deleteSub(deleted);
        noteService.deleteByFolderId(folderId);
    }
}
