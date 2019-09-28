package com.app.dao;

import com.app.models.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteDAO extends CrudRepository<Note, Long> {

}
