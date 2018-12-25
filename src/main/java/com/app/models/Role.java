package com.app.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name_role", length = 20)
    private String nameRole;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

}
