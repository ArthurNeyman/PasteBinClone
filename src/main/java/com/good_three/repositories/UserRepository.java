package com.good_three.repositories;

import com.good_three.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByUsername(String userName);
}
