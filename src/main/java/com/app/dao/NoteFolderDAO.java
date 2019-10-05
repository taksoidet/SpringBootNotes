package com.app.dao;

import com.app.models.NoteFolder;
import org.springframework.data.repository.CrudRepository;

public interface NoteFolderDAO extends CrudRepository<NoteFolder, Long> {
}
