package com.paste_bin_clone.repositories;

import com.paste_bin_clone.entities.AccessEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public interface AccessRepository extends CrudRepository<AccessEntity,Long> {
}
