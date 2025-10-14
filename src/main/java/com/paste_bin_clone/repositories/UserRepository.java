package com.paste_bin_clone.repositories;

import com.paste_bin_clone.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String userName);

    Optional<UserEntity> findOptionalByUsername(String userName);

    @Query(nativeQuery = true,
            value = "select (count(*) > 0) from users where user_name like  ? ")
    boolean userNameExist(String userName);
}
