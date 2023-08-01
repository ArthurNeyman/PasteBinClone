package com.paste_bin_clone.repositories;

import com.paste_bin_clone.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {

    Role findByName(String roleName);

}
