package com.paste_bin_clone.repositories;

import com.paste_bin_clone.entities.LifeTimeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifeTimeRepository extends CrudRepository<LifeTimeEntity,Long> {
}
