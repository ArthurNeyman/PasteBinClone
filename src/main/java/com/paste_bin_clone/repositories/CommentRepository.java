package com.paste_bin_clone.repositories;

import com.paste_bin_clone.entities.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity,Long> {

}
