package com.app.controllers;

import com.app.dao.NoteDAO;
import com.app.dao.UserDAO;
import com.app.models.Note;
import com.app.models.User;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/main")
public class MainController {

    private NoteDAO noteDAO;
    private UserDAO userDAO;

    @Autowired
    public MainController(NoteDAO noteDAO, UserDAO userDAO){
        this.noteDAO=noteDAO;
        this.userDAO=userDAO;

    }

    @GetMapping
    public String getLogin(Model model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userDAO.findByName(username);
        Collection<Note> notes=user.getNotes();
        model.addAttribute("notes", notes);
        model.addAttribute("username", user.getName());
        return "main";
    }

    @PostMapping
    public String createNote(String title, String text){
        Note note=new Note();
        note.setText(text);
        note.setTitle(title);
        note.setDate(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userDAO.findByName(username);
        note.setUser(user);
        noteDAO.save(note);
        return "redirect:/main";
    }

    @PostMapping(value = "delete/{id}")
    public String deleteNote(@PathVariable String id){
        noteDAO.deleteById(Long.valueOf(id));
        return "redirect:/main";
    }

    @PostMapping(value = "edit/{id}")
    public String editNote(@PathVariable String id, String title, String text){
       Note note=noteDAO.findById(Long.valueOf(id)).get();
       note.setTitle(title);
       note.setText(text);
       noteDAO.save(note);
       return "redirect:/main";
    }





}
