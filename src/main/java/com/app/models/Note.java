package com.app.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(length = Integer.MAX_VALUE)
    private String text;


    private String date;

    @ManyToOne
    @JoinColumn(name = "note_folder_id")
    private NoteFolder noteFolder;

    @Override
    public String toString(){
        return title+" "+text;
    }

}
