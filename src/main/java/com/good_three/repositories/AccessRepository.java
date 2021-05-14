package com.good_three.repositories;

import com.good_three.entities.AccessEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRepository extends CrudRepository<AccessEntity,Long> {
}
