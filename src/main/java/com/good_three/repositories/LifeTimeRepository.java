package com.good_three.repositories;

import com.good_three.entities.LifeTimeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeTimeRepository extends CrudRepository<LifeTimeEntity,Long> {
}
