package com.app.dao;

import com.app.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDAO extends CrudRepository<Role, Long> {
}
