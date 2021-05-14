package com.good_three.repositories;

import com.good_three.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {

    Role findByName(String roleName);

}
