package com.team.bang.folder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MemoryFolderRepository implements FolderRepository {
    private final Map<Long, Folder> db = new HashMap<>();
    private static long id = 0L;

    @Override
    public Folder save(Folder folder) {
        folder.setId(++id);
        db.put(folder.getId(), folder);
        log.info("save folder: {}", folder);
        return folder;
    }

    @Override
    public Optional<Folder> findById(long id) {
        Folder folder = db.get(id);
        log.info("findById folder: {}", folder);
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Folder> findByParent(Folder parent) {
        log.info("findByParent folder: {}", parent);
        return db.values().stream()
                .filter(folder -> folder.getParent() == parent)
                .toList();
    }

    @Override
    public void update(Folder newFolder) {
        db.put(newFolder.getId(), newFolder);
        log.info("update folder: {}", newFolder);
    }

    @Override
    public Folder delete(long id) {
        Folder remove = db.remove(id);
        log.info("delete folder: {}", remove);
        return remove;
    }

    public void clear() {
        db.clear();
    }
}
