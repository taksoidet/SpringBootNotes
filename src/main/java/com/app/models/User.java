package com.app.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 4)
    private String name;


    @Size(min = 6)
    private String password;

    @Transient
    private String confPassword;

    public User() {
    }

    @OneToMany(mappedBy = "user")
    private List<NoteFolder> noteFolders;

    @Override
    public String toString(){
        return this.getName();
    }


}
