package com.app.models;


import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class NoteFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "noteFolder")
    private Collection<Note> notes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return title;
    }

}
