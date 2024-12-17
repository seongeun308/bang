package com.team.bang.web;

import com.team.bang.folder.Folder;
import com.team.bang.folder.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final FolderService folderService;

    @GetMapping
    public String show(Model model) {
        List<Folder> topLevelFolders = folderService.getTopLevelFolders();
        model.addAttribute("folders", topLevelFolders);
        return "home";
    }

    @GetMapping("/folders/add")
    public String addFolder(Model model) {
        model.addAttribute("folder", new Folder());
        return "folder/addTopLevel";
    }

    @PostMapping("/folders/add")
    public String add(@ModelAttribute Folder folder) {
        folderService.add(folder);
        return "redirect:/";
    }
}
