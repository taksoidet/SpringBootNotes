package com.app.controllers;

import com.app.dao.NoteDAO;
import com.app.dao.NoteFolderDAO;
import com.app.dao.UserDAO;
import com.app.models.Note;
import com.app.models.NoteFolder;
import com.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/main/id")
public class MainController {


    private NoteDAO noteDAO;
    private UserDAO userDAO;
    private NoteFolderDAO noteFolderDAO;

    @Autowired
    public MainController(NoteDAO noteDAO, UserDAO userDAO, NoteFolderDAO noteFolderDAO) {
        this.noteDAO = noteDAO;
        this.userDAO = userDAO;
        this.noteFolderDAO = noteFolderDAO;
    }

    public Long getUserIdFromSession(HttpSession session) {
        return (Long) session.getAttribute("user_id");
    }

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {
        User user = userDAO.findById(getUserIdFromSession(session)).get();
        model.addAttribute("username", user.getName());
        List<NoteFolder> noteFolders = user.getNoteFolders();
        model.addAttribute("note_folders", noteFolders);
    }

    @GetMapping(value = "{folderId}")
    public String getLogin(@PathVariable Integer folderId, Model model, HttpSession session) {
        Optional<User> userOptional = userDAO.findById(getUserIdFromSession(session));
        User user = userOptional.get();
        List<NoteFolder> noteFolders = user.getNoteFolders();
        if (folderId > noteFolders.size() - 1) {
            folderId = 0;
        }
        Collection<Note> notes = noteFolders.get(folderId).getNotes();
        model.addAttribute("notes", notes);
        model.addAttribute("folderId", folderId);
        return "main";
    }

    @PostMapping(value = "{folderId}")
    public String createNote(@PathVariable Integer folderId, String title, String text, HttpSession session) {
        Note note = new Note();
        note.setText(text);
        note.setTitle(title);
        note.setDate(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
        User user = userDAO.findById(getUserIdFromSession(session)).get();
        List<NoteFolder> noteFolders = user.getNoteFolders();
        NoteFolder noteFolder = noteFolders.get(folderId);
        note.setNoteFolder(noteFolder);
        noteDAO.save(note);
        return "redirect:/main/id/" + folderId;
    }

    @PostMapping(value = "{folderId}/delete/{id}")
    public String deleteNote(@PathVariable Integer folderId, @PathVariable Long id) {
        noteDAO.deleteById(id);
        return "redirect:/main/id/" + folderId;
    }

    @PostMapping(value = "{folderId}/edit/{id}")
    public String editNote(@PathVariable Integer folderId, @PathVariable Long id, String title, String text) {
        Note note = noteDAO.findById(id).get();
        note.setTitle(title);
        note.setText(text);
        noteDAO.save(note);
        return "redirect:/main/id/" + folderId;
    }

    @PostMapping(value = "createfolder")
    public String createFolder(HttpSession session) {
        User user = userDAO.findById(getUserIdFromSession(session)).get();
        NoteFolder noteFolder = new NoteFolder();
        noteFolder.setTitle("New folder");
        noteFolder.setUser(user);
        user.getNoteFolders().add(noteFolder);
        noteFolderDAO.save(noteFolder);
        return "redirect:/main/id/" + (noteFolder.getId() - 1);
    }


}
