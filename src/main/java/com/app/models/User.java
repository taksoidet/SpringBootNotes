package com.app.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
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
    private Collection<Note> notes;



}
