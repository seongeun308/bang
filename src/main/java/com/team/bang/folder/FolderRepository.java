package com.team.bang.folder;

import java.util.List;
import java.util.Optional;

public interface FolderRepository {
    Folder save(Folder folder);
    Optional<Folder> findById(long id);
    List<Folder> findByParent(Folder parent);
    void update(Folder newFolder);
    Folder delete(long id);
}
