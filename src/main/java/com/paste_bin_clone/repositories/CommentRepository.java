package com.paste_bin_clone.repositories;

import com.paste_bin_clone.entities.CommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    @Transactional
    void deleteByIdAndUserId(long id, long userId);
    List<CommentEntity> findAllByUserId(long userId);
}
