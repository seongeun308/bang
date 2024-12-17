package com.team.bang.note;

import com.team.bang.folder.Folder;
import com.team.bang.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/folders/{folderId}/notes")
@Controller
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final FolderService folderService;

    @GetMapping("/add")
    public String addNote(@PathVariable("folderId") long folderId, Model model) {
        model.addAttribute("note", new Note());
        model.addAttribute("folderId", folderId);
        return "note/add";
    }

    @PostMapping("/add")
    public String addNote(@PathVariable("folderId") long folderId, @ModelAttribute Note note, RedirectAttributes redirect) {
        note.setFolder(folderService.getFolder(folderId));
        Note added = noteService.add(note);
        folderService.addNoteToFolder(folderId, added);
        redirect.addAttribute("folderId", folderId);
        return "redirect:/folders/{folderId}";
    }

    @GetMapping("/{noteId}")
    public String show(@PathVariable("noteId") long noteId, Model model) {
        Note note = noteService.getNote(noteId);
        model.addAttribute("note", note);
        return "note/view";
    }

    @GetMapping("/{noteId}/edit")
    public String editNote(@PathVariable("noteId") long noteId, Model model) {
        model.addAttribute("note", noteService.getNote(noteId));
        return "note/edit";
    }

    @PostMapping("/{noteId}/edit")
    public String editNote(@PathVariable("folderId") long folderId,
                           @PathVariable("noteId") long noteId,
                           @ModelAttribute Note note,
                           RedirectAttributes redirect) {
        Note oldNote = noteService.getNote(noteId);
        oldNote.setName(note.getName());
        noteService.modify(oldNote);
        redirect.addAttribute("folderId", folderId);
        redirect.addAttribute("noteId", noteId);
        return "redirect:/folders/{folderId}/notes/{noteId}";
    }

    @GetMapping("/{noteId}/remove")
    public String removeNote(@PathVariable("folderId") long folderId, @PathVariable("noteId") long noteId, RedirectAttributes redirect) {
        Note deleted = noteService.deleteNoteAndSub(noteId);
        Folder folder = folderService.getFolder(folderId);
        folder.deleteNote(deleted);
        redirect.addAttribute("folderId", folderId);
        return "redirect:/folders/{folderId}";
    }

}
