package com.team.bang.card;

import com.team.bang.note.Note;
import com.team.bang.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/folders/{folderId}/notes/{noteId}/cards")
@Controller
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final NoteService noteService;

    @GetMapping("/add")
    public String addCard(Model model, @PathVariable("folderId") long folderId, @PathVariable("noteId") long noteId) {
        model.addAttribute("card", new Card());
        model.addAttribute("folderId", folderId);
        model.addAttribute("noteId", noteId);
        return "card/add";
    }

    @PostMapping("/add")
    public String addCard(@ModelAttribute("card") Card card,
                          @PathVariable("folderId") long folderId,
                          @PathVariable("noteId") long noteId, RedirectAttributes redirect) {
        Card added = cardService.add(card);
        noteService.addCardToNote(noteId, added);
        redirect.addAttribute("folderId", folderId);
        redirect.addAttribute("noteId", noteId);
        return "redirect:/folders/{folderId}/notes/{noteId}";
    }

    @GetMapping("/{cardId}")
    public String showCard(Model model, @PathVariable("cardId") long cardId) {
        model.addAttribute("card", cardService.get(cardId));
        return "card/view";
    }

    @GetMapping("/{cardId}/edit")
    public String editCard(Model model, @PathVariable("cardId") long cardId) {
        Card card = cardService.get(cardId);
        model.addAttribute("card", card);
        return "card/edit";
    }

    @PostMapping("/{cardId}/edit")
    public String editCard(@ModelAttribute Card card,
                           @PathVariable("cardId") long cardId,
                           @PathVariable("folderId") long folderId,
                           @PathVariable("noteId") long noteId, RedirectAttributes redirect) {
        Card oldCard = cardService.get(cardId);
        oldCard.setKeyword(card.getKeyword());
        oldCard.setContent(card.getContent());
        cardService.modify(oldCard);
        redirect.addAttribute("folderId", folderId);
        redirect.addAttribute("noteId", noteId);
        return "redirect:/folders/{folderId}/notes/{noteId}";
    }

    @GetMapping("/{cardId}/remove")
    public String removeCard(@PathVariable("cardId") long cardId, RedirectAttributes redirect) {
        Card removed = cardService.remove(cardId);
        Note note = removed.getNote();
        note.removeCard(removed);
        redirect.addAttribute("noteId", note.getId());
        redirect.addAttribute("folderId", note.getFolder().getId());
        return "redirect:/folders/{folderId}/notes/{noteId}";
    }
}
