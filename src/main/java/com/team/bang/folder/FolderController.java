package com.team.bang.folder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/folders/{folderId}")
@Controller
@RequiredArgsConstructor
@Slf4j
public class FolderController {
    private final FolderService folderService;

    @GetMapping
    public String show(Model model, @PathVariable("folderId") Long folderId) {
        Folder folder = folderService.getFolder(folderId);
        model.addAttribute("folder", folder);
        return "folder/view";
    }

    @GetMapping("/add")
    public String addFolder(Model model, @PathVariable("folderId") Long folderId) {
        model.addAttribute("folder", new Folder());
        model.addAttribute("folderId", folderId);
        return "folder/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Folder folder, @PathVariable("folderId") Long folderId, RedirectAttributes redirect) {
        folderService.addFolderToFolder(folder, folderId);
        redirect.addAttribute("folderId", folderId);
        return "redirect:/folders/{folderId}";
    }

    @GetMapping("/edit")
    public String editForm(Model model, @PathVariable("folderId") Long folderId) {
        Folder folder = folderService.getFolder(folderId);
        model.addAttribute("folder", folder);
        return "folder/edit";
    }

    @PostMapping("/edit")
    public String edit(@PathVariable("folderId") Long folderId, @ModelAttribute Folder folder, RedirectAttributes redirect) {
        Folder oldFolder = folderService.getFolder(folderId);
        oldFolder.setName(folder.getName());
        folderService.modify(oldFolder);
        redirect.addAttribute("folderId", folderId);
        return "redirect:/folders/{folderId}";
    }

    @GetMapping("/remove")
    public String remove(@PathVariable("folderId") Long folderId, RedirectAttributes redirect) {
        Folder folder = folderService.getFolder(folderId);
        folderService.delete(folderId);

        Folder parent = folder.getParent();
        if (parent != null) {
            redirect.addAttribute("parentId", parent.getId());
            return "redirect:/folders/{parentId}";
        }
        return "redirect:/";
    }

    @PostConstruct
    public void init() {
        folderService.add(new Folder("폴더1", null));
        folderService.add(new Folder("폴더2", null));
    }
}
