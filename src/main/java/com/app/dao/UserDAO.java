package com.app.dao;

import com.app.models.User;
import org.springframework.data.repository.CrudRepository;


public interface UserDAO extends CrudRepository<User, Long> {
    User findByName(String name);
}
