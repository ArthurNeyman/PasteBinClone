package com.paste_bin_clone.repositories;

import com.paste_bin_clone.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByUserName(String userName);
}
